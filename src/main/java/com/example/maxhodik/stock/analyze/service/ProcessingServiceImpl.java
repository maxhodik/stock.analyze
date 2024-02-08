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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@AllArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {
    private final CompanyClient companyClient;
    private final StockClient stockClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;
    private final CompanyRepository companyRepository;
    private final StockRepository stockRepository;
    private List<String> tasks = new ArrayList<>();

    public List<Company> getCompanies() {
        return companyClient.getCompanies().stream()
                .filter(CompanyDto::isEnable)
                .limit(200)
                .map(companyMapper::convertToCompany)
                .map(this::addTask)
                .toList();
    }

    @Override
    public List<Stock> getStock() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        return tasks.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> stockClient.getStocks(s), executorService))
                .map(completableFuture -> completableFuture.thenApply(Optional::orElseThrow))
                .map(cf -> cf.thenApply(stockMapper::convertToStock))
                .map(CompletableFuture::join)
                .toList();
    }

    @Override
    public void saveCompanies(List<Company> companies) {
        companyRepository.saveAll(companies);
    }

    @Override
    public void saveStocks(List<Stock> stocks) {
        stockRepository.saveAll(stocks);
    }

    private Company addTask(Company company) {
        tasks.add(company.getSymbol());
        return company;
    }

}