package com.example.nvclothes.mappers;

import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.dto.TrousersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrousersEntityMapper {
    TrousersEntityMapper INSTANCE = Mappers.getMapper(TrousersEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    TrousersDto toDto(TrousersEntity trousersEntity);

    @Mapping(source = "orderDto", target= "order")
    TrousersEntity toEntity(TrousersDto trousersDto);
}
