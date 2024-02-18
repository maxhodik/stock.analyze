package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import reactor.core.publisher.Mono;

public interface CustomCompanyRepository {
    Mono<Company> saveCompany(Company company);
}
