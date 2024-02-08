package com.example.maxhodik.stock.analyze.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
@Data
@Builder
public class Stock {
    @Id
    private long id;
    @Column(name = "latest_price")
    private BigDecimal latestPrice;
    @Column(name = "delta")
    private BigDecimal delta;
    @Column(name = "company_name")
    private String companyName;


}
