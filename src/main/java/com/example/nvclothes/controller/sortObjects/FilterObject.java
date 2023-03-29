package com.example.nvclothes.controller.sortObjects;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterObject {

    @Nullable
    private String size;
    @Nullable
    private Long costFrom;
    @Nullable
    private Long costTo;
    @Nullable
    private String brand;
    @Nullable
    private String productType;
}
