package br.com.fiap3esa.spring_boot_project.repository;

import br.com.fiap3esa.spring_boot_project.entity.WellbeingEntry;
import br.com.fiap3esa.spring_boot_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WellbeingRepository extends JpaRepository<WellbeingEntry, Long> {
    List<WellbeingEntry> findByUserOrderByCreatedAtDesc(User user);
}