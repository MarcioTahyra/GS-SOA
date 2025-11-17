package br.com.fiap3esa.spring_boot_project.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVO {
    @Email
    @NotBlank
    private String email;
}