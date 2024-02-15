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
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "latest_price")
    private BigDecimal latestPrice;
    @Column(name = "delta")
    private BigDecimal delta;
    @Column(name = "company_name")
    private String companyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
