package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.config.Config;
import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.mapper.CompanyMapper;
import com.example.maxhodik.stock.analyze.mapper.StockMapper;
import com.example.maxhodik.stock.analyze.repository.CompanyRepository;
import com.example.maxhodik.stock.analyze.repository.CustomCompanyRepository;
import com.example.maxhodik.stock.analyze.repository.CustomStockRepository;
import com.example.maxhodik.stock.analyze.restClient.CompanyClient;
import com.example.maxhodik.stock.analyze.restClient.StockClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {

    private final Config config;
    private final CompanyClient companyClient;
    private final StockClient stockClient;
    private final CompanyMapper companyMapper;
    private final StockMapper stockMapper;
    private final CompanyRepository companyRepository;
    private final CustomCompanyRepository customCompanyRepository;
    private final CustomStockRepository customStockRepository;
    private final List<String> tasks = new CopyOnWriteArrayList<>();

    public Flux<Company> processingCompanies() {
        log.debug("Company dto List");
        return Flux.fromIterable(companyClient.getCompanies())
                .filter(CompanyDto::isEnabled)
                .take(config.getNumberOfCompaniesConfig())
                .map(companyMapper::mapToCompany)
                .map(this::addTask)
                .flatMap(this::addTaskAndSaveCompany);
    }

    private Mono<Company> addTaskAndSaveCompany(Company company) {
        return customCompanyRepository.saveCompany(company)
                .doOnSuccess(savedCompany -> log.info("Company {} processed", savedCompany.getSymbol()));
    }


    @Override
    public List<Stock> getStocks() {

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<Stock>> completableFutures = tasks.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> stockClient.getStock(s), executorService))
                .map(completableFuture -> completableFuture.thenApply(optional ->
                        optional.orElse(null)))
                .filter(Objects::nonNull)
                .map(cf -> cf.thenApply(stockMapper::mapToStock))
                .toList();

        log.info("List of Completable Future of Stocks");
        return completableFutures.stream()
                .map(CompletableFuture::join)
                .toList();
    }


    @Override
    public Mono<Void> saveStocks(List<Stock> stocks) {
        log.debug("Try save stock before flux");
//        List<Disposable> disposables = stocks.stream()
//                .filter(Objects::nonNull)
//                .map(customStockRepository::saveStock)
//                .map(Mono::subscribe)
//                .toList();
        return Flux.fromIterable(stocks)
                .filter(Objects::nonNull)
                .map(customStockRepository::saveStock)
                .map(Mono::subscribe)
                .then();

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
