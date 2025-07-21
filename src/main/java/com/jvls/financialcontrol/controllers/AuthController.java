package com.jvls.financialcontrol.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jvls.financialcontrol.constants.Routes;
import com.jvls.financialcontrol.dtos.LoginResponseDTO;
import com.jvls.financialcontrol.dtos.SignInDTO;
import com.jvls.financialcontrol.dtos.UserCreationDTO;
import com.jvls.financialcontrol.entities.User;
import com.jvls.financialcontrol.exceptions.FinancialControlException;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.services.AuthenticationService;
import com.jvls.financialcontrol.services.JwtService;
import com.jvls.financialcontrol.utils.URIUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(Routes.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@Valid @RequestBody UserCreationDTO dto) throws FinancialControlException {
        UUID userId = authenticationService.signup(dto);
        return ResponseEntity.created(URIUtil.getUri(userId, "api/user")).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@Valid @RequestBody SignInDTO dto) throws InfoNotFoundException {
        User authenticatedUser = authenticationService.signin(dto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        var responseDto = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(responseDto);
    }
}
