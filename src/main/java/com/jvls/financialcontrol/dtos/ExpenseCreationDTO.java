package com.jvls.financialcontrol.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import com.jvls.financialcontrol.enums.EnumBuyMethod;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCreationDTO {

    private String description;

    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "1000000", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal amount;

    @NotNull(message = "Wallet is mandatory")
    private UUID idWallet;

    @NotNull(message = "Buy method is mandatory")
    private EnumBuyMethod buyMethod;

}
