package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.mapper.CompanyMapper;
import com.example.maxhodik.stock.analyze.repository.CustomCompanyRepository;
import com.example.maxhodik.stock.analyze.restClient.CompanyClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

@SpringBootTest(properties = "scheduling.enabled=false")
class ProcessingServiceImplTest {
    private final Company EXPECTED_COMPANY = new Company(1L, "symbol", "companyName", true);
    private final CompanyDto EXPECTED_COMPANY_DTO = new CompanyDto("symbol", "companyName", true);
    private final List<Company> COMPANIES_LIST = List.of(EXPECTED_COMPANY);
    private final List<CompanyDto> COMPANIES_DTO_LIST = List.of(EXPECTED_COMPANY_DTO);
    @MockBean
    private CompanyClient companyClient;
    @MockBean
    private CompanyMapper companyMapper;
    @MockBean
    private CustomCompanyRepository customCompanyRepository;
    private ProcessingService processingService;

    @Test
    void processingCompanies() {
        //Given
        when(companyClient.getCompanies()).thenReturn(COMPANIES_DTO_LIST);
        when(companyMapper.mapToCompany(EXPECTED_COMPANY_DTO)).thenCallRealMethod();

        //WHEN
//        List<Disposable> result = processingService.processingCompanies();

        //THEN
    }

    @Test
    void getStocks() {
    }

    @Test
    void saveStocks() {
    }

    @Test
    void getCompaniesFromDB() {
    }
}