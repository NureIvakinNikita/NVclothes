package com.example.nvclothes.nvclothes.mappers;

import com.example.nvclothes.nvclothes.dto.TrousersDto;
import com.example.nvclothes.nvclothes.entity.products.TrousersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrousersEntityMapper {
    TrousersEntityMapper INSTANCE = Mappers.getMapper(TrousersEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    TrousersDto toDto(TrousersEntity trousersEntity);

    @Mapping(source = "order", target= "orderDto")
    TrousersEntity toEntity(TrousersDto trousersDto);
}
