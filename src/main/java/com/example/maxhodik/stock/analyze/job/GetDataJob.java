package com.example.maxhodik.stock.analyze.job;

import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Log4j2
@RequiredArgsConstructor
@EnableScheduling
public class GetDataJob {

    private final ProcessingService processingService;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void runCompanyGetDataJob() {
        processingService.processingCompanies().subscribe();
//        processingService.getCompaniesFromDB();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void runStockDataJob() {
        List<Stock> stocks = processingService.getStocks();
        log.info("List of stocks {}", stocks);
        CompletableFuture.supplyAsync(() -> processingService.saveStocks(stocks), executorService)
                .thenApply(Mono::subscribe)
                .thenAccept((i) -> log.info("Saving stocks was completed"))
                .join();


    }


}
