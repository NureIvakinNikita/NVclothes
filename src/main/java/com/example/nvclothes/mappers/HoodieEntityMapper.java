package com.example.nvclothes.nvclothes.mappers;

import com.example.nvclothes.nvclothes.dto.HoodieDto;
import com.example.nvclothes.nvclothes.entity.products.HoodieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HoodieEntityMapper {

    HoodieEntityMapper INSTANCE = Mappers.getMapper(HoodieEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    HoodieDto toDto(HoodieEntity hoodieEntity);

    @Mapping(source = "order", target = "orderDto")
    HoodieEntity toEntity(HoodieDto hoodieDto);

}
