package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface StockRepository extends R2dbcRepository<Stock, Integer> {
    @Query("SELECT symbol, company_name, latest_price FROM stock_analyze.stock ORDER BY `latest_price` DESC LIMIT 5;")
    Flux<Stock> find5MostExpensiveStocks();

    @Query("SELECT symbol, company_name, delta FROM stock_analyze.stock ORDER BY `delta` DESC LIMIT 5;")
    Flux<Stock> find5BiggestDelta();


}
