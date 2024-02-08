package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company convertToCompany(CompanyDto companyDto) {
        return Company.builder()
                .symbol(companyDto.symbol())
                .companyName(companyDto.companyName())
                .build();
    }
}
