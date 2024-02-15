package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
@RequiredArgsConstructor
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {
    private final R2dbcEntityTemplate r2dbcTemplate;


    @Override
    public void saveCompanies(List<Company> companies) {
        log.info("Insert companies into DB");
        companies.stream()
                .peek(System.out::println)
                .forEach(r2dbcTemplate::insert);
        log.info("End inserting companies into DB");
    }
}
