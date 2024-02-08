package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;

import java.util.List;

public interface CompanyClient {
    List<CompanyDto> getCompanies();
}
