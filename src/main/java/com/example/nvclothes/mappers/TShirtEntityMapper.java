package com.example.nvclothes.mappers;

import com.example.nvclothes.dto.TShirtDto;
import com.example.nvclothes.entity.products.TShirtEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TShirtEntityMapper {

    TShirtEntityMapper INSTANCE = Mappers.getMapper(TShirtEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    TShirtDto toDto(TShirtEntity tShirtEntity);

    @Mapping(source = "orderDto", target = "order")
    TShirtEntity toEntity(TShirtDto tShirtDto);
}
