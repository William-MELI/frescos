package com.meli.frescos.repository;

import com.meli.frescos.model.RepresentativeModel;
import com.meli.frescos.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepresentativeRepository extends JpaRepository<RepresentativeModel, Long> {

    @Query("SELECT representative FROM RepresentativeModel representative WHERE representative.warehouse.id = :warehouseId")
    RepresentativeModel findRepresentativeModelByWarehouseId(@Param("warehouseId") Long warehouseId);
}
