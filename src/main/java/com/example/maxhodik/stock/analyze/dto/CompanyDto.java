package com.example.maxhodik.stock.analyze.dto;


import org.springframework.data.annotation.Id;

public record CompanyDto(@Id Long id, String symbol, String name, Boolean isEnabled) {
}
