package com.meli.frescos.controller.dto;

import com.meli.frescos.model.Refrigerator;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefrigeratorResponse {

    private Long id;

    private String brand;

    private String model;

    private Double temperature;

    private Boolean turnedOn;

    private LocalDateTime acquired;

    private LocalDateTime lastRevision;

    private SectionResponse section;

    public static RefrigeratorResponse toResponse(Refrigerator refrigerator) {
        return RefrigeratorResponse.builder()
                .id(refrigerator.getId())
                .brand(refrigerator.getBrand())
                .model(refrigerator.getModel())
                .temperature(refrigerator.getTemperature())
                .turnedOn(refrigerator.getTurnedOn())
                .acquired(refrigerator.getAcquired())
                .lastRevision(refrigerator.getLastRevision())
                .section(refrigerator.getSection() == null? null : SectionResponse.toResponse(refrigerator.getSection()))
                .build();
    }
}
