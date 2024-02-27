package com.example.maxhodik.stock.analyze.repository;

import com.example.maxhodik.stock.analyze.entity.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.core.ReactiveSelectOperation;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomStockRepositoryImplTest {
    private final Stock EXPECTED_STOCK = new Stock(1L, "symbol", BigDecimal.TEN, null, "companyName");
    @Mock
    private R2dbcEntityTemplate r2dbcEntityTemplate;
    @InjectMocks
    private CustomStockRepositoryImpl repository;

    @Test
    void saveStock_WhenNewStock() {
        // GIVEN
        ReactiveSelectOperation.ReactiveSelect<Stock> reactiveSelectMock = mock(ReactiveSelectOperation.ReactiveSelect.class);
        ReactiveSelectOperation.TerminatingSelect<Stock> reactiveTerminatingMock = mock(ReactiveSelectOperation.TerminatingSelect.class);

        when(r2dbcEntityTemplate.select(Stock.class))
                .thenReturn(reactiveSelectMock);

        when(reactiveSelectMock.matching(any())).thenReturn(reactiveTerminatingMock);

        when(reactiveTerminatingMock.one())
                .thenReturn(Mono.empty());

        when(r2dbcEntityTemplate.insert(EXPECTED_STOCK))
                .thenReturn(Mono.just(EXPECTED_STOCK));
        // WHEN
        repository.saveStock(EXPECTED_STOCK)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // THEN
        verify(r2dbcEntityTemplate).select(Stock.class);
        verify(r2dbcEntityTemplate).insert(EXPECTED_STOCK);
        verify(r2dbcEntityTemplate, never()).update(EXPECTED_STOCK);
    }

    @Test
    void saveStock_WhenNot_NewStock() {
        // GIVEN
        ReactiveSelectOperation.ReactiveSelect<Stock> reactiveSelectMock = mock(ReactiveSelectOperation.ReactiveSelect.class);
        ReactiveSelectOperation.TerminatingSelect<Stock> reactiveTerminatingMock = mock(ReactiveSelectOperation.TerminatingSelect.class);

        when(r2dbcEntityTemplate.select(Stock.class))
                .thenReturn(reactiveSelectMock);

        when(reactiveSelectMock.matching(any())).thenReturn(reactiveTerminatingMock);

        when(reactiveTerminatingMock.one())
                .thenReturn(Mono.just(EXPECTED_STOCK));

        when(r2dbcEntityTemplate.update(EXPECTED_STOCK))
                .thenReturn(Mono.just(EXPECTED_STOCK));
        // WHEN
        repository.saveStock(EXPECTED_STOCK)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        // THEN
        verify(r2dbcEntityTemplate).select(Stock.class);
        verify(r2dbcEntityTemplate, never()).insert(EXPECTED_STOCK);
        verify(r2dbcEntityTemplate).update(EXPECTED_STOCK);
    }
}