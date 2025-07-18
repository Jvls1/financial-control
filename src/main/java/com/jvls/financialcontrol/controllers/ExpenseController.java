package com.jvls.financialcontrol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jvls.financialcontrol.constants.Routes;
import com.jvls.financialcontrol.dtos.ExpenseCreationDTO;
import com.jvls.financialcontrol.dtos.ExpenseResponseDTO;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.services.ExpenseService;
import com.jvls.financialcontrol.utils.URIUtil;

import jakarta.validation.Valid;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.EXPENSE)
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @RequestBody ExpenseCreationDTO expenseParam) throws InfoNotFoundException {
        var expense = expenseService.save(expenseParam);
        return ResponseEntity.created(URIUtil.getUri(expense.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponseDTO>> findAll(@RequestParam Integer page, @RequestParam Integer row) {
        return ResponseEntity.ok(expenseService.findAllExpense(page, row));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable("id") UUID idExpense) throws InfoNotFoundException {
        Optional<ExpenseResponseDTO> expense = expenseService.findByIdExpense(idExpense);
        if (expense.isEmpty()) {
            throw new InfoNotFoundException("Expense not found");
        }
        return ResponseEntity.ok(expense.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") UUID idExpense) {
        expenseService.deleteById(idExpense);
        return ResponseEntity.noContent().build();
    }
}
