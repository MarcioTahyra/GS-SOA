package br.com.fiap3esa.spring_boot_project.service;

import br.com.fiap3esa.spring_boot_project.dto.AuthRequestDTO;
import br.com.fiap3esa.spring_boot_project.dto.AuthResponseDTO;
import br.com.fiap3esa.spring_boot_project.entity.User;
import br.com.fiap3esa.spring_boot_project.enums.RoleName;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.repository.UserRepository;
import br.com.fiap3esa.spring_boot_project.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public ResponseEntity<ApiResponse<?>> register(AuthRequestDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Email já cadastrado"));
        }

        User user = User.builder()
                .email(dto.getEmail())
                .fullName(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(RoleName.ROLE_USER))
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.ok(null, "Usuário criado com sucesso"));
    }

    public ResponseEntity<ApiResponse<?>> authenticate(AuthRequestDTO dto) {
        try {
            var auth = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(auth);

            var user = userRepository.findByEmail(dto.getEmail()).orElseThrow();
            Set<String> roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
            String token = jwtUtil.generateToken(user.getEmail(), roles);

            AuthResponseDTO resp = AuthResponseDTO.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .expiresInMs(jwtUtil.getExpirationMs())
                    .build();

            return ResponseEntity.ok(ApiResponse.ok(resp, "Autenticado"));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(ApiResponse.error("Credenciais inválidas"));
        }
    }
}