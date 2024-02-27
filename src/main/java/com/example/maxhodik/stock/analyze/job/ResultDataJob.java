package com.example.maxhodik.stock.analyze.job;

import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.service.AnalyzeStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.CompletableFuture;

@Component
@Log4j2
@EnableScheduling
@RequiredArgsConstructor
public class ResultDataJob {
    private final AnalyzeStockService stockService;

    @Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void RunResultJob() {

        CompletableFuture.runAsync(() -> {
                    Flux<Stock> mostExpensiveStocks = stockService.find5MostExpensiveStocks();
                    Flux<Stock> biggestDelta = stockService.find5BiggestDelta();
                    stockService.printReport5MostExpensiveStocks(mostExpensiveStocks);
                    stockService.printReportBiggestDelta(biggestDelta);
                }
        ).join();
    }
}
