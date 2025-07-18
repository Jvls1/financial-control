package com.jvls.financialcontrol.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jvls.financialcontrol.dtos.IncomeCreationDTO;
import com.jvls.financialcontrol.dtos.IncomeResponseDTO;
import com.jvls.financialcontrol.entities.Income;
import com.jvls.financialcontrol.entities.Wallet;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.repositories.IIncomeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncomeService {

    private final IIncomeRepository iIncomeRepository;
    private final SessionService sessionService;
    private final WalletService walletService;


    public Page<IncomeResponseDTO> findAllIncome(Integer page, Integer row) {
        Page<Income> incomePage = iIncomeRepository.findAllByWalletWalletOwnerId(PageRequest.of(page, row), sessionService.sessionUser().getId());

        List<IncomeResponseDTO> expenseResponseTOS = incomePage.getContent()
                .stream()
                .map(income -> new IncomeResponseDTO().setValues(income))
                .collect(Collectors.toList());

        return new PageImpl<>(expenseResponseTOS);
    }

    public Optional<IncomeResponseDTO> findByIdIncome(UUID idIncome) throws InfoNotFoundException {
        Optional<Income> incomeOpt = iIncomeRepository.findByIdAndWalletWalletOwnerId(idIncome, sessionService.sessionUser().getId());
        if(incomeOpt.isEmpty()) {
            return Optional.empty();
        }
        IncomeResponseDTO incomeResponseTO = new IncomeResponseDTO();
        incomeResponseTO.setValues(incomeOpt.get());
        return Optional.of(incomeResponseTO);
    }

    public void save(Income income) {
        iIncomeRepository.save(income);
    }

    public Income save(IncomeCreationDTO incomeCreationTO) throws InfoNotFoundException {
        Optional<Wallet> walletOptional = walletService.findById(incomeCreationTO.getIdWallet());
        if (walletOptional.isEmpty()) {
            throw new InfoNotFoundException("Wallet not found");
        }
        Income income = new Income();
        income.setDescription(incomeCreationTO.getDescription());
        income.setAmount(incomeCreationTO.getAmount());
        income.setWallet(walletOptional.get());

        return iIncomeRepository.save(income);
    }

    public void deleteById(UUID income) {
        iIncomeRepository.deleteByIncomeId(income);
    }
}
