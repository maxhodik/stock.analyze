package com.example.maxhodik.stock.analyze.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "company")
@Builder
public class Company {

    @Id
    private long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "company_name")
    private String companyName;
}
