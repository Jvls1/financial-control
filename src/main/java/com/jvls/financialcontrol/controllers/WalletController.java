package com.jvls.financialcontrol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jvls.financialcontrol.constants.Routes;
import com.jvls.financialcontrol.dtos.WalletCreationDTO;
import com.jvls.financialcontrol.entities.Wallet;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.services.WalletService;
import com.jvls.financialcontrol.utils.URIUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.WALLET)
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<Object> createWallet(@RequestBody WalletCreationDTO walletParam) throws InfoNotFoundException {
        var wallet = walletService.save(walletParam);
        return ResponseEntity.created(URIUtil.getUri(wallet.getId())).build();
    }

    @GetMapping()
    public ResponseEntity<Page<Wallet>> getWalletAll(@RequestParam Integer page, @RequestParam Integer row) throws InfoNotFoundException {
        Page<Wallet> walletPage = walletService.findAllWalletsByOwner(PageRequest.of(page, row));
        if (walletPage.isEmpty()) {
            throw new InfoNotFoundException("Wallet not found");
        }
        return ResponseEntity.ok(walletPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWalletById(@PathVariable("id") UUID idWallet) {
        Optional<Wallet> wallet = walletService.findById(idWallet);
        if (wallet.isEmpty()) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(wallet.get());
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<Wallet>> getWalletByOwnerId(@PathVariable("id") UUID idWallet) throws InfoNotFoundException {
        List<Wallet> wallets = walletService.findByWalletOwnerId(idWallet);
        if(wallets.isEmpty()) {
            throw new InfoNotFoundException("Wallet not found");
        }
        return ResponseEntity.ok(wallets);
    }

    @PostMapping("/collaborator")
    public ResponseEntity<Object> addCollaboratorToWallet(@RequestParam UUID idWallet, @RequestParam UUID idUser)
            throws InfoNotFoundException {
        walletService.addCollaboratorToWallet(idWallet, idUser);
        return ResponseEntity.ok("Add");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") UUID idWallet) {
        walletService.deleteById(idWallet);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/collaborator")
    public ResponseEntity<Object> removeCollaboratorFromWallet(@RequestParam UUID idWallet, @RequestParam UUID idUser)
            throws InfoNotFoundException {
        walletService.removeCollaboratorFromWallet(idWallet, idUser);
        return ResponseEntity.ok("Removed");
    }
}
