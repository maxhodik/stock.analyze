package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CompanyMapperTest {
    private final Company EXPECTED_COMPANY = new Company(null, "symbol", "companyName", true);
    private final String SYMBOL = "symbol";
    private final String COMPANY_NAME = "companyName";
    private final CompanyDto EXPECTED_COMPANY_DTO = new CompanyDto("symbol", "companyName", true);
    @InjectMocks
    private CompanyMapper companyMapper;

    @Test
    void mapToCompany() {
        //WHEN
        Company company = companyMapper.mapToCompany(EXPECTED_COMPANY_DTO);
        //THEN
        assertEquals(SYMBOL, company.getSymbol());
        assertEquals(COMPANY_NAME, company.getCompanyName());
    }
}