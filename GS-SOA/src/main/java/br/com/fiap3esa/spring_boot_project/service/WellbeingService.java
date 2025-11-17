package br.com.fiap3esa.spring_boot_project.service;


import br.com.fiap3esa.spring_boot_project.dto.WellbeingDTO;
import br.com.fiap3esa.spring_boot_project.entity.User;
import br.com.fiap3esa.spring_boot_project.entity.WellbeingEntry;
import br.com.fiap3esa.spring_boot_project.exception.ResourceNotFoundException;
import br.com.fiap3esa.spring_boot_project.payload.ApiResponse;
import br.com.fiap3esa.spring_boot_project.repository.UserRepository;
import br.com.fiap3esa.spring_boot_project.repository.WellbeingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WellbeingService {

    private final WellbeingRepository wellbeingRepository;
    private final UserRepository userRepository;

    public WellbeingService(WellbeingRepository wellbeingRepository, UserRepository userRepository) {
        this.wellbeingRepository = wellbeingRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<ApiResponse<?>> createEntry(Authentication authentication, WellbeingDTO dto) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        WellbeingEntry entry = WellbeingEntry.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .mood(dto.getMood())
                .sleepHours(dto.getSleepHours())
                .stressLevel(dto.getStressLevel())
                .notes(dto.getNotes())
                .build();

        WellbeingEntry saved = wellbeingRepository.save(entry);

        WellbeingDTO out = WellbeingDTO.builder()
                .id(saved.getId())
                .mood(saved.getMood())
                .sleepHours(saved.getSleepHours())
                .stressLevel(saved.getStressLevel())
                .notes(saved.getNotes())
                .build();

        return ResponseEntity.ok(ApiResponse.ok(out, "Entrada registrada"));
    }

    public ResponseEntity<ApiResponse<?>> listMyEntries(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        List<WellbeingDTO> list = wellbeingRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(e -> WellbeingDTO.builder()
                        .id(e.getId())
                        .mood(e.getMood())
                        .sleepHours(e.getSleepHours())
                        .stressLevel(e.getStressLevel())
                        .notes(e.getNotes())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.ok(list, "Entradas do usuário"));
    }
}