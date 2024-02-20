package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper1 {

    CompanyDto mapToCompany(Company company);

    @InheritInverseConfiguration
    Company mapToCompanyDto(CompanyDto companyDTO);

}
