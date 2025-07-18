package com.jvls.financialcontrol.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "income")
@Getter
@Setter
@NoArgsConstructor
public class Income extends BaseEntity {

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "amount", precision = 8, scale = 2, nullable = false)
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "1000000", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_wallet", nullable = false)
    private Wallet wallet;
}
