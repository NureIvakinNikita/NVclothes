package com.example.nvclothes.controller.sortObjects;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class FilterObject {

    @Nullable
    private String size;
    @Nullable
    @Min(value = 0, message = "CostFrom cannot be less than 0.")
    @Max(value = 1000000, message = "CostFrom can not be bigger than 1000000.")
    private Long costFrom;
    @Nullable
    @Min(value = 0, message = "CostFrom cannot be less than 0.")
    @Max(value = 1000000, message = "CostFrom can not be bigger than 1000000.")
    private Long costTo;
    @Nullable
    private String brand;
    @Nullable
    private String productType;
}
