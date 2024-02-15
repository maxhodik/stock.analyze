package com.example.maxhodik.stock.analyze.job;

import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class GetDataJob {

    private final ProcessingService processingService;

    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void runCompanyGetDataJob() {
        List<Company> companies = processingService.getCompanies();
        processingService.saveCompanies(companies);
//        processingService.getCompaniesFromDB();
    }

//    @Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void runStockDataJob() {
        List<Stock> stocks = processingService.getStock();
        processingService.saveStocks(stocks);

    }


}
