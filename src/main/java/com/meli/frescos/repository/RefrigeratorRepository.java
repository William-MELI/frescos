package com.meli.frescos.repository;

import com.meli.frescos.model.Refrigerator;
import com.meli.frescos.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {
    Optional<Refrigerator> findBySection(SectionModel section);
}
