package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.mapper.CompanyMapper;
import com.example.maxhodik.stock.analyze.mapper.StockMapper;
import com.example.maxhodik.stock.analyze.repository.CompanyRepository;
import com.example.maxhodik.stock.analyze.repository.StockRepository;
import com.example.maxhodik.stock.analyze.restClient.CompanyClient;
import com.example.maxhodik.stock.analyze.restClient.StockClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {
    private final CompanyClient companyClient;
    private final StockClient stockClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;
    private final CompanyRepository companyRepository;
    private final StockRepository stockRepository;
    private List<String> tasks = new ArrayList<>();

    public List<Company> getCompanies() {
        log.info("Company dto List");
        return companyClient.getCompanies().stream()
                .filter(CompanyDto::isEnabled)
                .limit(200)
                .peek(System.out::println)
                .map(companyMapper::convertToCompany)
                .map(this::addTask)
                .toList();
    }

    @Override
    public List<Stock> getStock() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<Stock>> completableFutures = tasks.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> stockClient.getStocks(s), executorService))
                .map(completableFuture -> completableFuture.thenApply(Optional::orElseThrow))
                .map(cf -> cf.thenApply(stockMapper::convertToStock)).toList();
        return completableFutures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    @Override
    public void saveCompanies(List<Company> companies) {
        log.info("Start saving companies");
        Flux<Company> companyFlux = companyRepository.saveAll(companies);
        companyFlux.subscribe(System.out::println);
        log.info("End saving companies");
    }

    @Override
    public void saveStocks(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }

    @Override
    public Flux<Company> getCompaniesFromDB() {
        Flux<Company> all = companyRepository.findAll();
        log.info("Getting list companies");
        all.subscribe(System.out::println);
        return all;
    }

    private Company addTask(Company company) {
        tasks.add(company.getSymbol());
        return company;
    }

}
