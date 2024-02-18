package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@RequiredArgsConstructor
public class CustomStockRepositoryImpl implements CustomStockRepository {
    private final R2dbcEntityTemplate r2dbcTemplate;


    @Override
    public Stock saveStock(Stock stock) {
        log.info("Start saving stocks");
        Stock savedStock = r2dbcTemplate.insert(stock).block();
        log.info("End saving stocks");
        return savedStock;
    }
}
