package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
@RequiredArgsConstructor
public class CustomStockRepositoryImpl implements CustomStockRepository {
    private final R2dbcEntityTemplate r2dbcTemplate;


    public Mono<Stock> saveStock(Stock stock) {
        log.info("Start saving stocks");


        return r2dbcTemplate.select(Stock.class)
                .matching(Query.query(Criteria.where("symbol").is(stock.getSymbol())))
                .one()
                .flatMap(s -> {
                    if (s == null) {
                        log.info("Stock {} inserted", stock.getSymbol());
                        return r2dbcTemplate.insert(stock);
                    }
                    log.info("Stock {} updated", stock.getSymbol());
                    s.setLatestPrice(stock.getLatestPrice());
                    return r2dbcTemplate.update(s);
                });

    }
}
