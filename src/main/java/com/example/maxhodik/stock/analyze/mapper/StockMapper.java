package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import com.example.maxhodik.stock.analyze.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock convertToStock(StockDto stockDto) {
        return Stock.builder()
                .latestPrice(stockDto.latestPrice())
                //todo delta
                .build();
    }
}
