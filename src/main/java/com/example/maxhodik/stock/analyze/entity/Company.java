package com.example.maxhodik.stock.analyze.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Table("company")
@Builder
public class Company implements Persistable<Long> {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    @Column("company_name")
    private String companyName;
    @Transient
    private Boolean isNew = true;

    @Override
    public boolean isNew() {
        return null == getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!id.equals(company.id)) return false;
        return symbol.equals(company.symbol);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
