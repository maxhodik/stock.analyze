package com.example.maxhodik.stock.analyze.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
@Data
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "latest_price")
    private BigDecimal latestPrice;
    @Column(name = "delta")
    private BigDecimal delta;
    @Column(name = "company_name")
    private String companyName;


}
