package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import com.example.maxhodik.stock.analyze.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock mapToStock(StockDto stockDto) {
        if (stockDto == null) {
            return null;
        }
        return Stock.builder()
                .latestPrice(stockDto.latestPrice())
                .companyName(stockDto.companyName())
                .symbol(stockDto.symbol())
                .build();
    }
}
