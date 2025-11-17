package br.com.fiap3esa.spring_boot_project.controller;


import br.com.fiap3esa.spring_boot_project.dto.WellbeingDTO;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.service.WellbeingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wellbeing")
public class WellbeingController {

    private final WellbeingService service;

    public WellbeingController(WellbeingService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/entries")
    public ResponseEntity<ApiResponse<?>> createEntry(Authentication authentication, @Valid @RequestBody WellbeingDTO dto) {
        return service.createEntry(authentication, dto);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/entries")
    public ResponseEntity<ApiResponse<?>> listEntries(Authentication authentication) {
        return service.listMyEntries(authentication);
    }
}