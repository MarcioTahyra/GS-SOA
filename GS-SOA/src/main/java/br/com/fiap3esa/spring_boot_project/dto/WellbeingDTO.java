package br.com.fiap3esa.spring_boot_project.dto;


import br.com.fiap3esa.spring_boot_project.enums.Mood;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WellbeingDTO {
    private Long id;

    @NotNull
    private Mood mood;

    @Min(0)
    @Max(24)
    private Double sleepHours;

    @Min(0)
    @Max(10)
    private Integer stressLevel;

    private String notes;
}