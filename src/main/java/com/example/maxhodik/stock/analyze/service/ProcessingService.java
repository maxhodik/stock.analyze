package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProcessingService {
    Flux<Company> processingCompanies();

    List<Stock> getStocks();

    Mono<Void> saveStocks(List<Stock> stocks);

    Flux<Company> getCompaniesFromDB();
}
