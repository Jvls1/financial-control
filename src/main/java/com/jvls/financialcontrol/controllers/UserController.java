package com.jvls.financialcontrol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jvls.financialcontrol.constants.Routes;
import com.jvls.financialcontrol.dtos.UserCreationDTO;
import com.jvls.financialcontrol.entities.User;
import com.jvls.financialcontrol.exceptions.InfoNotFoundException;
import com.jvls.financialcontrol.services.UserService;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Routes.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") UUID idUser) throws InfoNotFoundException {
        Optional<User> user = userService.findById(idUser);
        if (user.isEmpty()) {
            throw new InfoNotFoundException("User not founded");
        }
        return ResponseEntity.ok().body(user.get());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable("id") UUID idUser, @RequestBody UserCreationDTO userCreationTO) throws Exception {
        userService.updateUser(idUser, userCreationTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID idUser) throws InfoNotFoundException {
        userService.deleteById(idUser);
        return ResponseEntity.noContent().build();
    }
}
