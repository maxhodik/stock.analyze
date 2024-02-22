package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import com.example.maxhodik.stock.analyze.entity.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class StockMapperTest {
    private final Stock EXPECTED_STOCK = new Stock(null, "symbol", BigDecimal.TEN, null, "companyName");
    private final StockDto EXPECTED_STOCK_DTO = new StockDto(BigDecimal.TEN, "companyName", "symbol");
    @InjectMocks
    private StockMapper stockMapper;

    @Test
    void mapToStock() {
        //When
        Stock stock = stockMapper.mapToStock(EXPECTED_STOCK_DTO);
        //Then
        assertEquals(EXPECTED_STOCK.getSymbol(), stock.getSymbol());
        assertEquals(EXPECTED_STOCK.getCompanyName(), stock.getCompanyName());
        assertEquals(EXPECTED_STOCK.getLatestPrice(), stock.getLatestPrice());
        assertEquals(EXPECTED_STOCK.getDelta(), stock.getDelta());
    }

    @Test
    void whenDtoIsNullShouldReturnNull() {
        //When
        Stock stock = stockMapper.mapToStock(null);
        //Then
        assertNull(stock);
    }
}