package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
@RequiredArgsConstructor
public class AnalyzeStockServiceImpl implements AnalyzeStockService {
    private final StockRepository stockRepository;

    @Override
    public Flux<Stock> find5MostExpensiveStocks() {
        log.info("Receiving top 5 most expensive stocks");
        return CompletableFuture.supplyAsync(stockRepository::find5MostExpensiveStocks
                )
                .join();
    }

    @Override
    public Flux<Stock> find5BiggestDelta() {
        log.info("Receiving top 5 biggest delta");
        return CompletableFuture.supplyAsync(stockRepository::find5BiggestDelta)
                .join();
    }

    @Override
    public void printReport5MostExpensiveStocks(Flux<Stock> stockFlux) {
        AtomicInteger atomicInteger = new AtomicInteger();
        stockFlux.subscribe(stock ->
                System.out.printf("%s. The most expensive stock. Company: %s , price: %s \n",
                        atomicInteger.incrementAndGet(),
                        stock.getCompanyName(), stock.getLatestPrice()));
    }

    @Override
    public void printReportBiggestDelta(Flux<Stock> stockFlux) {
        AtomicInteger atomicInteger = new AtomicInteger();
        stockFlux.subscribe(stock ->
                System.out.printf("%s. The biggest stock delta. Company: %s , delta: %s \n", atomicInteger.incrementAndGet(),
                        stock.getCompanyName(), stock.getDelta()));
    }
}
