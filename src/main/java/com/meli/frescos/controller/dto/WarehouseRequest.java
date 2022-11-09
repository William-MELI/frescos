package com.meli.frescos.controller.dto;

import javax.validation.constraints.NotBlank;

public class WarehouseRequest {

    @NotBlank
    String localization;

}
