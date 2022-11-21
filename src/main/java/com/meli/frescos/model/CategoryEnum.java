package com.meli.frescos.model;

import com.meli.frescos.exception.BatchStockFilterCategoryInvalidException;
import lombok.Getter;

@Getter
public enum CategoryEnum {
    FRESH("FS"),
    FROZEN("FF"),
    REFRIGERATED("RF");

    private String code;

    CategoryEnum(String code){
        this.code = code;
    }

    public static CategoryEnum getEnum(String code){
        return switch (code.toUpperCase()) {
            case "FS" -> CategoryEnum.FRESH;
            case "FF" -> CategoryEnum.FROZEN;
            case "RF" -> CategoryEnum.REFRIGERATED;
            default -> throw new BatchStockFilterCategoryInvalidException(code);
        };
    }

}
