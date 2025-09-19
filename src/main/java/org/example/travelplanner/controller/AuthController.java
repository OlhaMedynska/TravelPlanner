package org.example.travelplanner.controller;

import org.example.travelplanner.dto.LoginRequestDTO;
import org.example.travelplanner.dto.UserDTO;
import org.example.travelplanner.security.JwUtil;
import org.example.travelplanner.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwUtil jwUtil;

    public AuthController(UserService userService, JwUtil jwUtil) {
        this.userService = userService;
        this.jwUtil = jwUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        UserDTO user = userService.login(request.getUsername(), request.getPassword());
        return jwUtil.generateToken(user.getUsername());
    }
}
