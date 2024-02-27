package com.example.maxhodik.stock.analyze.service;

import com.example.maxhodik.stock.analyze.entity.Stock;
import com.example.maxhodik.stock.analyze.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyzeStockServiceImplTest {
    private final Stock EXPECTED_STOCK1 = new Stock(1L, "symbol", BigDecimal.TEN, null, "companyName");
    private final Stock EXPECTED_STOCK2 = new Stock(2L, "symbol2", BigDecimal.TEN, null, "companyName2");
    private final Stock EXPECTED_STOCK3 = new Stock(3L, "symbol3", BigDecimal.TEN, null, "companyName3");
    private final Flux<Stock> STOCKS = Flux.just(EXPECTED_STOCK1, EXPECTED_STOCK2, EXPECTED_STOCK3);
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private AnalyzeStockServiceImpl service;

    @Test
    void find5MostExpensiveStocks() {
        // GIVEN
        when(stockRepository.find5MostExpensiveStocks()).thenReturn(STOCKS);
        // WHEN
        Flux<Stock> mostExpensiveStocks = service.find5MostExpensiveStocks();
        // THEN
        verify(stockRepository, atLeastOnce()).find5MostExpensiveStocks();
        StepVerifier
                .create(mostExpensiveStocks)
                .assertNext(stock -> {
                    assertThat(stock).isNotNull();
                    assertThat(stock.getId()).isEqualTo(EXPECTED_STOCK1.getId());
                    assertThat(stock.getSymbol()).isEqualTo(EXPECTED_STOCK1.getSymbol());
                    assertThat(stock.getCompanyName()).isEqualTo(EXPECTED_STOCK1.getCompanyName());
                })
                .expectNext(EXPECTED_STOCK2, EXPECTED_STOCK3)
                .expectNextCount(0L)
                .expectComplete()
                .verify();

    }

    @Test
    void find5BiggestDelta() {
        when(stockRepository.find5BiggestDelta()).thenReturn(STOCKS);
        // WHEN
        Flux<Stock> mostExpensiveStocks = service.find5BiggestDelta();
        // THEN
        verify(stockRepository, atLeastOnce()).find5BiggestDelta();
        StepVerifier
                .create(mostExpensiveStocks)
                .assertNext(stock -> {
                    assertThat(stock).isNotNull();
                    assertThat(stock.getId()).isEqualTo(EXPECTED_STOCK1.getId());
                    assertThat(stock.getSymbol()).isEqualTo(EXPECTED_STOCK1.getSymbol());
                    assertThat(stock.getCompanyName()).isEqualTo(EXPECTED_STOCK1.getCompanyName());
                })
                .expectNext(EXPECTED_STOCK2, EXPECTED_STOCK3)
                .expectNextCount(0L)
                .expectComplete()
                .verify();

    }
}
