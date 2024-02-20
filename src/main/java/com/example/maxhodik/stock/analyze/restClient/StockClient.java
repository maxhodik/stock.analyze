package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.StockDto;

import java.util.Optional;

// todo smth with url
public interface StockClient {

    Optional<StockDto> getStock(String tasks);
}
