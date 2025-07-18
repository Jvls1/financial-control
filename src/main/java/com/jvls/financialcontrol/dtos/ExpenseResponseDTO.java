package com.jvls.financialcontrol.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.jvls.financialcontrol.entities.Expense;
import com.jvls.financialcontrol.enums.EnumBuyMethod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {

    private String description;

    private BigDecimal amount;

    private LocalDate dateRegister;

    private UUID idWallet;

    private EnumBuyMethod enumBuyMethod;

    public ExpenseResponseDTO setValues(Expense expense) {
        description = expense.getDescription();
        amount = expense.getAmount();
        idWallet = expense.getWallet().getId();
        enumBuyMethod = expense.getEnumBuyMethod();
        return this;
    }

}
