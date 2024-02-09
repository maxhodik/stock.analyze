package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface StockRepository extends R2dbcRepository<Stock, Long> {
//public interface StockRepository extends JpaRepository<Stock, Long> {
}
