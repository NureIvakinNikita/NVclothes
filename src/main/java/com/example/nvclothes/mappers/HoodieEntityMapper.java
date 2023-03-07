package com.example.nvclothes.mappers;

import com.example.nvclothes.dto.HoodieDto;
import com.example.nvclothes.entity.products.HoodieEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HoodieEntityMapper {

    HoodieEntityMapper INSTANCE = Mappers.getMapper(HoodieEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    HoodieDto toDto(HoodieEntity hoodieEntity);

    @Mapping(source = "orderDto", target = "order")
    HoodieEntity toEntity(HoodieDto hoodieDto);

}
