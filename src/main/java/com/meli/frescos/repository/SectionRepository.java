package com.meli.frescos.repository;

import com.meli.frescos.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<SectionModel, Long> {
}
