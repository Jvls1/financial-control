package com.jvls.financialcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.jvls.financialcontrol.entities.Income;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponseDTO {

    private String description;

    private BigDecimal amount;

    private LocalDate dateRegister;

    private UUID idWallet;

    public IncomeResponseDTO setValues(Income income) {
        description = income.getDescription();
        amount = income.getAmount();
        idWallet = income.getWallet().getId();
        return this;
    }

}
