package com.example.maxhodik.stock.analyze.mapper;

import com.example.maxhodik.stock.analyze.dto.StockDto;
import com.example.maxhodik.stock.analyze.entity.Stock;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper1 {

    StockDto mapToStock(Stock stock);

    @InheritInverseConfiguration
    Stock mapToStockDto(StockDto stockDto);
}
