package br.com.fiap3esa.spring_boot_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}