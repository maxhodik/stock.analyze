package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;

import java.util.List;

public interface ProcessingService {
    List<Company> getCompanies();

    List<Stock> getStock();

    void saveCompanies(List<Company> companies);

    void saveStocks(List<Stock> stocks);
}
