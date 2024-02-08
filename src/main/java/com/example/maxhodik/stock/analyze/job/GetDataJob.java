package com.example.maxhodik.stock.analyze.job;

import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.service.ProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetDataJob {

    private final ProcessingService processingService;

    //    @Scheduled(fixedDelay = 3600 * 1000, initialDelay = 100)
    public void runCompanyGetDataJob() {
        List<Company> companies = processingService.getCompanies();
        processingService.saveCompanies(companies);
    }

    //    @Scheduled(fixedDelay = 5000, initialDelay = 3000)
    public void runStockDataJob() {
        List<Stock> stocks = processingService.getStock();
        processingService.saveStocks(stocks);

    }

}
