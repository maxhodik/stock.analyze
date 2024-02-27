package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Stock;
import reactor.core.publisher.Flux;

public interface AnalyzeStockService {

    Flux<Stock> find5MostExpensiveStocks();

    Flux<Stock> find5BiggestDelta();

    void printReport5MostExpensiveStocks(Flux<Stock> stockFlux);

    void printReportBiggestDelta(Flux<Stock> stockFlux);

}
