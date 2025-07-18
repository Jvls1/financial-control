package com.jvls.financialcontrol.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.jvls.financialcontrol.constants.Routes;
import com.jvls.financialcontrol.dtos.ExpenseCreationDTO;
import com.jvls.financialcontrol.entities.Expense;
import com.jvls.financialcontrol.enums.EnumBuyMethod;
import com.jvls.financialcontrol.repositories.IExpenseRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ExpenseControllerTest extends BaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private IExpenseRepository iExpenseRepository;

    @Test
    void findAll() {
        final String requestUrl = "http://localhost:" + randomServerPort + Routes.EXPENSE;

        HttpEntity<Object> request = new HttpEntity<>(getJwtAuthenticationHeader());

        ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(201, result.getStatusCode().value());
    }

    @Test
    void getExpenseById() {
        List<Expense> expenses = iExpenseRepository.findAll();
        Expense expense = expenses.get(0);
        Assertions.assertNotNull(expense);

        final String requestUrl = "http://localhost:" + randomServerPort + Routes.EXPENSE + "/" + expense.getId();

        HttpEntity<Object> request = new HttpEntity<>(getJwtAuthenticationHeader());

        ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void save() {
        final String requestUrl = "http://localhost:" + randomServerPort + Routes.EXPENSE;

        ExpenseCreationDTO expenseCreationTO = new ExpenseCreationDTO();
        expenseCreationTO.setAmount(new BigDecimal("10"));
        expenseCreationTO.setDescription("Test");
        /**
         * TODO:
         *
         * Change this UUID to a valid one later.
         */
        expenseCreationTO.setIdWallet(new UUID(1, 2));
        expenseCreationTO.setBuyMethod(EnumBuyMethod.OTHER);

        HttpEntity<Object> request = new HttpEntity<>(expenseCreationTO, getJwtAuthenticationHeader());

        ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void deleteById() {
        List<Expense> expenses = iExpenseRepository.findAll();
        Expense expense = expenses.get(0);
        Assertions.assertNotNull(expense);

        final String requestUrl = "http://localhost:" + randomServerPort + Routes.EXPENSE + "/" + expense.getId();

        HttpEntity<Object> request = new HttpEntity<>(getJwtAuthenticationHeader());

        ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.DELETE, request, String.class);
        Assertions.assertEquals(200, result.getStatusCode().value());
    }
}