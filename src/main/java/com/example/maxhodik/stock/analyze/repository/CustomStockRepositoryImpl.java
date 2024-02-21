package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

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
                    if (s != null) {
                        // Stock already exists, update it
                        BigDecimal delta = s.getLatestPrice().subtract(stock.getLatestPrice());
                        log.debug("Stock symbol {}, Old Price{}, new price{}, delta{}", s.getSymbol(), s.getLatestPrice(), stock.getLatestPrice(), delta);
                        s.setLatestPrice(stock.getLatestPrice());
                    }
                    return r2dbcTemplate.update(s)
                            .doOnSuccess(updatedStock -> log.info("Stock {} updated", updatedStock.getSymbol()));
                })
                .switchIfEmpty(r2dbcTemplate.insert(stock)
                        .doOnSuccess(newStock -> log.info("Stock {} inserted", newStock.getSymbol()))
                        .then(Mono.just(stock)));
    }
}



