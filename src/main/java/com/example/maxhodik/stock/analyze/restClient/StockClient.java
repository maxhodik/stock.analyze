package com.example.maxhodik.stock.analyze.restClient;

import com.example.maxhodik.stock.analyze.dto.StockDto;

// todo smth with url
public interface StockClient {

    StockDto getStock(String tasks);
}
