package com.example.nvclothes.nvclothes.mappers;

import com.example.nvclothes.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.nvclothes.entity.products.AccessoriesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccessoriesEntityMapper {

    AccessoriesEntityMapper INSTANCE = Mappers.getMapper(AccessoriesEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    AccessoriesDto toDto(AccessoriesEntity accessoriesEntity);
    @Mapping(source = "order", target = "orderDto")
    AccessoriesEntity toEntity(AccessoriesDto accessoriesDto);

}