package br.com.fiap3esa.spring_boot_project.controller;


import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService svc;

    public UserController(UserService svc) {
        this.svc = svc;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me(Authentication authentication) {
        return svc.getProfile(authentication);
    }
}