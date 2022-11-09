package com.meli.frescos.repository;

import com.meli.frescos.model.SectionModel;
import com.meli.frescos.model.WarehouseModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWarehouseRepository extends CrudRepository<WarehouseModel, Long> {
    @Query("SELECT section FROM SectionModel section join section.warehouse wh WHERE section.warehouse.id = :warehouseId")
    List<SectionModel> findSectionByWarehouseModelId(@Param("warehouseId") Long warehouseId);
}
