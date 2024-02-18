package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


public interface CompanyRepository extends R2dbcRepository<Company, Long> {
    @Query("SELECT EXISTS(SELECT 1 FROM company WHERE symbol = :symbol)")
    Mono<Boolean> existsBySymbol(String symbol);


    Mono<Object> findBySymbol(String symbol);
}


