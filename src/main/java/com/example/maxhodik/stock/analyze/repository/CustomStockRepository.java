package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;

public interface CustomStockRepository {
    Stock saveStock(Stock stock);
}
