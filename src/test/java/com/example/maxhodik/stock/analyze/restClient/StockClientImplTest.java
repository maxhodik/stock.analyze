package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
class StockClientImplTest {
    private final StockDto STOCK_DTO = new StockDto(BigDecimal.TEN, "companyName", "symbol");
    private final String TASK = "A";
    private final ResponseEntity<StockDto> RESPONSE_ENTITY_OK = new ResponseEntity<>(STOCK_DTO, HttpStatus.OK);
    private final ResponseEntity<StockDto> RESPONSE_ENTITY_NO_CONTENT = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    private final String URL = "http://example.com/stocks.%s";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private StockClientImpl stockClient;

    @Test
    public void testGetStocks_SuccessfulResponse() {
        // GIVEN
        ReflectionTestUtils.setField(stockClient, "url", URL);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(null), eq(StockDto.class)))
                .thenReturn(RESPONSE_ENTITY_OK);
        // WHEN
        Optional<StockDto> stock = stockClient.getStock(TASK);
        // THEN
        assertEquals(STOCK_DTO, stock.get());
    }

    @Test
    public void testGetStocks_Exception_ShouldBeEmpty() {
        // GIVEN
        ReflectionTestUtils.setField(stockClient, "url", URL);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(null), eq(StockDto.class)))
                .thenThrow(HttpClientErrorException.class);
        // WHEN
        Optional<StockDto> stock = stockClient.getStock(TASK);
        // THEN
        assertEquals(Optional.empty(), stock);
    }


    @Test
    public void testGetStocks_EmptyResponse() {
        // GIVEN
        ReflectionTestUtils.setField(stockClient, "url", URL);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), eq(null), eq(StockDto.class)))
                .thenReturn(RESPONSE_ENTITY_NO_CONTENT);
        // WHEN
        Optional<StockDto> stock = stockClient.getStock(TASK);
        // THEN
        assertEquals(Optional.empty(), stock);
    }
}