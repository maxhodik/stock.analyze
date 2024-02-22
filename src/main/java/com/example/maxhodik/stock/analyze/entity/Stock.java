package com.example.maxhodik.stock.analyze.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;


@Table("stock")
@Data
@Builder
@AllArgsConstructor
public class Stock {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column("symbol")
    private String symbol;
    @Column("latest_price")
    private BigDecimal latestPrice;
    @Column("delta")
    private BigDecimal delta;
    @Column("company_name")
    private String companyName;
//    @Transient
//    private boolean newEntity = true;

//    @Override
//    public Long getId() {
//        return id;
//    }
//
//
//    @Override
//    public boolean isNew() {
//        return null == getId();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (!Objects.equals(id, stock.id)) return false;
        return symbol.equals(stock.symbol);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
