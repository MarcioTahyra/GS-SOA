package br.com.fiap3esa.spring_boot_project.entity;

import br.com.fiap3esa.spring_boot_project.enums.Mood;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "wellbeing_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WellbeingEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    private Double sleepHours;

    private Integer stressLevel;

    @Column(columnDefinition = "TEXT")
    private String notes;
}