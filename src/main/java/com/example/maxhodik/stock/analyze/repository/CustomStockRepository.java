package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import reactor.core.publisher.Mono;

public interface CustomStockRepository {
    Mono<Stock> saveStock(Stock stock);
}
