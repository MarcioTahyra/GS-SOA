package br.com.fiap3esa.spring_boot_project.controller;


import br.com.fiap3esa.spring_boot_project.dto.AuthRequestDTO;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody AuthRequestDTO request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody AuthRequestDTO request) {
        return service.authenticate(request);
    }
}