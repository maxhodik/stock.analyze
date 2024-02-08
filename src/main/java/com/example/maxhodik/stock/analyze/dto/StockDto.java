package com.example.maxhodik.stock.analyze.dto;

import java.math.BigDecimal;

public record StockDto(BigDecimal latestPrice, String companyName) {
}
