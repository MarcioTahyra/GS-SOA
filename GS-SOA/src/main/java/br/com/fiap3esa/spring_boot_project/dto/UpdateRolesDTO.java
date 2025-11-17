package br.com.fiap3esa.spring_boot_project.dto;


import br.com.fiap3esa.spring_boot_project.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRolesDTO {
    @NotNull(message = "roles não pode ser nulo")
    private Set<RoleName> roles;
}