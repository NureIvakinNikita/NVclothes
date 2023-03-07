package com.example.nvclothes.mappers;

import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.dto.AccessoriesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessoriesEntityMapper {

    AccessoriesEntityMapper INSTANCE = Mappers.getMapper(AccessoriesEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    AccessoriesDto toDto(AccessoriesEntity accessoriesEntity);
    @Mapping(source = "orderDto", target = "order")
    AccessoriesEntity toEntity(AccessoriesDto accessoriesDto);

}