package com.jvls.financialcontrol.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import com.jvls.financialcontrol.enums.EnumBuyMethod;

@Entity
@Table(name = "expense")
@Getter
@Setter
@NoArgsConstructor
public class Expense extends BaseEntity {

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "amount", precision = 8, scale = 2, nullable = false)
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "1000000", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @Column(name = "buy_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumBuyMethod enumBuyMethod;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wallet", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;
}
