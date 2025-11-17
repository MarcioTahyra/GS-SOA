package br.com.fiap3esa.spring_boot_project.service;

import br.com.fiap3esa.spring_boot_project.dto.UpdateRolesDTO;
import br.com.fiap3esa.spring_boot_project.dto.UserDTO;
import br.com.fiap3esa.spring_boot_project.entity.User;
import br.com.fiap3esa.spring_boot_project.enums.RoleName;
import br.com.fiap3esa.spring_boot_project.exception.ResourceNotFoundException;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<List<UserDTO>>> listUsers() {
        List<UserDTO> users = userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.ok(users, "Lista de usuários"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<UserDTO>> updateUserRoles(Long userId, UpdateRolesDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + userId));

        Set<RoleName> newRoles = dto.getRoles();
        user.setRoles(newRoles);
        User saved = userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.ok(toDto(saved), "Roles atualizadas"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + userId));

        userRepository.deleteById(userId);
        return ResponseEntity.ok(ApiResponse.ok(null, "Usuário removido com sucesso"));
    }

    private UserDTO toDto(User u) {
        return UserDTO.builder()
                .id(u.getId())
                .email(u.getEmail())
                .fullName(u.getFullName())
                .roles(u.getRoles())
                .build();
    }
}