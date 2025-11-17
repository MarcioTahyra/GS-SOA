package br.com.fiap3esa.spring_boot_project.service;


import br.com.fiap3esa.spring_boot_project.dto.UserDTO;
import br.com.fiap3esa.spring_boot_project.entity.User;
import br.com.fiap3esa.spring_boot_project.exception.ResourceNotFoundException;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<ApiResponse<?>> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User u = repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        UserDTO dto = UserDTO.builder()
                .id(u.getId())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .roles(u.getRoles())
                .build();
        return ResponseEntity.ok(ApiResponse.ok(dto, "Perfil recuperado"));
    }
}