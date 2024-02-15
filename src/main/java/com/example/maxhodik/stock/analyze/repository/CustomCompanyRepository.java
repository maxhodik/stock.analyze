package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Company;

import java.util.List;

public interface CustomCompanyRepository {
 void saveCompanies(List<Company> companies);
}
