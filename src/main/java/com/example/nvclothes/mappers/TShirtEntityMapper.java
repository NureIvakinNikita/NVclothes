package com.example.nvclothes.nvclothes.mappers;

import com.example.nvclothes.nvclothes.dto.TShirtDto;
import com.example.nvclothes.nvclothes.entity.products.TShirtEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TShirtEntityMapper {

    TShirtEntityMapper INSTANCE = Mappers.getMapper(TShirtEntityMapper.class);

    @Mapping(source = "order", target = "orderBy")
    TShirtDto toDto(TShirtEntity tShirtEntity);

    @Mapping(source = "order", target = "orderDto")
    TShirtEntity toEntity(TShirtDto tShirtDto);
}
