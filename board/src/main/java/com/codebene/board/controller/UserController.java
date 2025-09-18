package com.codebene.board.controller;

import com.codebene.board.model.user.User;
import com.codebene.board.model.user.UserAuthenticationResponse;
import com.codebene.board.model.user.UserLoginRequestBody;
import com.codebene.board.model.user.UserSignUpRequestBody;
import com.codebene.board.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String query) {
        var users = userService.getUsers(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> signUp(@Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody) {
        var user = userService.signUp(
                userSignUpRequestBody.username(),
                userSignUpRequestBody.password()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@Valid @RequestBody UserLoginRequestBody userLoginRequestBody) {
        var response = userService.authenticate(
                userLoginRequestBody.username(),
                userLoginRequestBody.password()
        );
        return ResponseEntity.ok(response);
    }
}
