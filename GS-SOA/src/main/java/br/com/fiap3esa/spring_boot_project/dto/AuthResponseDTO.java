package br.com.fiap3esa.spring_boot_project.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private String token;
    private String tokenType = "Bearer";
    private Long expiresInMs;
}