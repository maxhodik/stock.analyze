package com.example.maxhodik.stock.analyze.dto;


import org.springframework.data.annotation.Id;

public record CompanyDto(@Id long id, String symbol, String name, Boolean isEnabled) {
}
