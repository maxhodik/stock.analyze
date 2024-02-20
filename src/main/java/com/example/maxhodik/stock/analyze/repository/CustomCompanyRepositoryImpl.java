package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
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
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {
    private final R2dbcEntityTemplate r2dbcTemplate;

    public Mono<Company> saveCompany(Company company) {
        return r2dbcTemplate.select(Company.class)
                .matching(Query.query(Criteria.where("symbol").is(company.getSymbol())))
                .one()
                .switchIfEmpty(r2dbcTemplate.insert(company)
                        .doOnSuccess(c -> log.info("Company with symbol {} was inserted", c.getSymbol())));

    }

}