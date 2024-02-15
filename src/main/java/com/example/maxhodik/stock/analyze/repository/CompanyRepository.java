package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends R2dbcRepository<Company, Long> {
//public interface CompanyRepository extends JpaRepository<Company, Long> {

}

