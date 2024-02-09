package com.example.maxhodik.stock.analyze.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "company")
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "company_name")
    private String companyName;
}
