package com.example.maxhodik.stock.analyze.dto;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public record StockDto(@Id long id, BigDecimal latestPrice, String companyName, String symbol) {
}
