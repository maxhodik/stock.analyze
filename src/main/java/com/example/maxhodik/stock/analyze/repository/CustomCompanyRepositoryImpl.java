package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCompanyRepositoryImpl implements CustomCompanyRepository {
    private final R2dbcEntityTemplate r2dbcTemplate;


    @Override
    public void saveCompanies(List<Company> companies) {

        companies.forEach(r2dbcTemplate::insert);


    }
}
