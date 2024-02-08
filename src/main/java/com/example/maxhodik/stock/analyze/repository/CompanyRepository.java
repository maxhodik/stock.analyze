package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CompanyRepository extends R2dbcRepository<Company, Long> {

}

