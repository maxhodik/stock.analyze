package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ProcessingService {
    List<Disposable> processingCompanies();

    List<Stock> getStock();

    void saveStocks(List<Stock> stocks);

    Flux<Company> getCompaniesFromDB();
}
