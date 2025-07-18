package com.jvls.financialcontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jvls.financialcontrol.dtos.ExpenseCreationDTO;
import com.jvls.financialcontrol.dtos.ExpenseResponseDTO;
import com.jvls.financialcontrol.entities.Expense;
import com.jvls.financialcontrol.entities.Wallet;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.repositories.IExpenseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final IExpenseRepository iExpenseRepository;
    private final SessionService sessionService;
    private final WalletService walletService;

    public Page<ExpenseResponseDTO> findAllExpense(Integer page, Integer row) {
        Page<Expense> expensePage = iExpenseRepository.findAllByWalletWalletOwnerId(PageRequest.of(page, row), sessionService.sessionUser().getId());

        List<ExpenseResponseDTO> expenseResponseTOS = expensePage.getContent()
                .stream()
                .map(expense -> new ExpenseResponseDTO().setValues(expense))
                .collect(Collectors.toList());

        return new PageImpl<>(expenseResponseTOS);
    }

    public Optional<ExpenseResponseDTO> findByIdExpense(UUID idExpense) {
        Optional<Expense> expenseOpt = iExpenseRepository.findByIdAndWalletWalletOwnerId(idExpense, sessionService.sessionUser().getId());
        if (expenseOpt.isEmpty()) {
            return Optional.empty();
        }
        ExpenseResponseDTO expenseResponseTO = new ExpenseResponseDTO();
        expenseResponseTO.setValues(expenseOpt.get());
        return Optional.of(expenseResponseTO);
    }

    public Expense save(ExpenseCreationDTO expenseCreationTO) throws InfoNotFoundException {
        Optional<Wallet> walletOptional = walletService.findByIdAndWalletOwnerId(expenseCreationTO.getIdWallet(), sessionService.sessionUser().getId());
        if (walletOptional.isEmpty()) {
            throw new InfoNotFoundException("Wallet not found");
        }
        Expense expense = new Expense();
        expense.setAmount(expenseCreationTO.getAmount());
        expense.setDescription(expenseCreationTO.getDescription());
        expense.setWallet(walletOptional.get());
        expense.setEnumBuyMethod(expenseCreationTO.getBuyMethod());

        return iExpenseRepository.save(expense);
    }

    public void deleteById(UUID idExpense) {
        iExpenseRepository.deleteByExpenseId(idExpense);
    }
}
