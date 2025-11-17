package br.com.fiap3esa.spring_boot_project.controller;


import br.com.fiap3esa.spring_boot_project.dto.UpdateRolesDTO;
import br.com.fiap3esa.spring_boot_project.dto.UserDTO;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService service) {
        this.adminService = service;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> listUsers() {
        return adminService.listUsers();
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<ApiResponse<UserDTO>> updateRoles(@PathVariable("id") Long id,
                                                            @Valid @RequestBody UpdateRolesDTO dto) {
        return adminService.updateUserRoles(id, dto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable("id") Long id) {
        return adminService.deleteUser(id);
    }
}