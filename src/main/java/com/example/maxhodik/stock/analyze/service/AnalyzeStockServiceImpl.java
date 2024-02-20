package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
@RequiredArgsConstructor
public class AnalyzeStockServiceImpl implements AnalyzeStockService {
    private final StockRepository stockRepository;

    @Override
    public void find5MostExpensiveStocks() {
        log.info("Receiving top 5 most expensive stocks");
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.supplyAsync(() -> stockRepository.find5MostExpensiveStocks().subscribe(stock ->
                System.out.printf("%s. The most expensive stock. Company: %s , price: %s \n", atomicInteger.incrementAndGet(),
                        stock.getCompanyName(), stock.getLatestPrice())
        )).join();

    }

    @Override
    public void find5BiggestDelta() {
        log.info("Receiving top 5 biggest delta");
        AtomicInteger atomicInteger = new AtomicInteger();
        CompletableFuture.supplyAsync(() ->
                stockRepository.find5BiggestDelta().subscribe(stock ->
                        System.out.printf("%s. The biggest stock delta. Company: %s , delta: %s \n", atomicInteger.incrementAndGet(),
                                stock.getCompanyName(), stock.getDelta()))).join();
    }
}
