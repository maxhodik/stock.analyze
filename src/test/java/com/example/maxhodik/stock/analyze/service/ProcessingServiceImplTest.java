package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.dto.CompanyDto;
import com.example.maxhodik.stock.analyze.dto.StockDto;
import com.example.maxhodik.stock.analyze.entity.Company;
import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.mapper.CompanyMapper;
import com.example.maxhodik.stock.analyze.mapper.StockMapper;
import com.example.maxhodik.stock.analyze.repository.CustomCompanyRepository;
import com.example.maxhodik.stock.analyze.repository.CustomStockRepository;
import com.example.maxhodik.stock.analyze.restClient.CompanyClient;
import com.example.maxhodik.stock.analyze.restClient.StockClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

//@SpringBootTest(properties = "scheduling.enabled=false")
@ExtendWith(MockitoExtension.class)
class ProcessingServiceImplTest {
    private static final List<String> TASKS = List.of("symbol");
    private final Company EXPECTED_COMPANY = new Company(1L, "symbol", "companyName", true);
    private final CompanyDto EXPECTED_COMPANY_DTO = new CompanyDto("symbol", "companyName", true);
    private final List<Company> COMPANIES_LIST = List.of(EXPECTED_COMPANY);
    private final List<CompanyDto> COMPANIES_DTO_LIST = List.of(EXPECTED_COMPANY_DTO);
    private final Stock EXPECTED_STOCK = new Stock(null, "symbol", BigDecimal.TEN, null, "companyName");
    private final StockDto EXPECTED_STOCK_DTO = new StockDto(BigDecimal.TEN, "companyName", "symbol");
    private final List<Stock> EXPECTED_STOCKS_LIST = List.of(EXPECTED_STOCK);
    private final List<StockDto> EXPECTED_STOCKS_GTO_LIST = List.of(EXPECTED_STOCK_DTO);
    @Mock
    private CompanyClient companyClient;
    @Mock
    private StockClient stockClient;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private StockMapper stockMapper;
    @Mock
    private CustomCompanyRepository customCompanyRepository;
    @Mock
    private CustomStockRepository customStockRepository;
    @InjectMocks
    private ProcessingServiceImpl processingService;

    @Captor
    private ArgumentCaptor<Company> companyArgumentCaptor;

    @Test
    void processingCompanies() {
        //Given
        when(companyClient.getCompanies()).thenReturn(COMPANIES_DTO_LIST);
        when(companyMapper.mapToCompany(EXPECTED_COMPANY_DTO)).thenReturn(EXPECTED_COMPANY);
        when(customCompanyRepository.saveCompany(any(Company.class))).thenReturn(Mono.just(EXPECTED_COMPANY));
        //WHEN
        Company result = processingService.processingCompanies().blockFirst();
        //THEN
        verify(companyClient).getCompanies();
        verify(companyMapper).mapToCompany(any());
        verify(customCompanyRepository).saveCompany(companyArgumentCaptor.capture());
        Company value = companyArgumentCaptor.getValue();
        assertEquals(EXPECTED_COMPANY, value);
        assertEquals(EXPECTED_COMPANY, result);
    }

    @Test
    void getStocks() {
        //Given
        ReflectionTestUtils.setField(processingService, "tasks", TASKS);
        when(stockClient.getStock(any(String.class))).thenReturn(Optional.of(EXPECTED_STOCK_DTO));
        when(stockMapper.mapToStock(EXPECTED_STOCK_DTO)).thenReturn(EXPECTED_STOCK);
        //WHEN
        List<Stock> stocks = processingService.getStocks();
        //THEN
        verify(stockClient).getStock(any(String.class));
        verify(stockMapper).mapToStock(any());

        assertEquals(EXPECTED_STOCKS_LIST, stocks);
        assert stocks.size() == 1;
    }

    @Test
    void saveStocks() {
        //Given
        when(customStockRepository.saveStock(any(Stock.class))).thenReturn(Mono.just(EXPECTED_STOCK));
        //WHEN
        processingService.saveStocks(EXPECTED_STOCKS_LIST).subscribe();
        //THEN
        verify(customStockRepository).saveStock(any(Stock.class));
    }

    @Test
    void getCompaniesFromDB() {
    }
}