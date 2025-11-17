package br.com.fiap3esa.spring_boot_project.dto;


import br.com.fiap3esa.spring_boot_project.enums.RoleName;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private Set<RoleName> roles;
}