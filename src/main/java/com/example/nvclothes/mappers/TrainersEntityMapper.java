package com.example.nvclothes.nvclothes.mappers;

import com.example.nvclothes.nvclothes.dto.TrainersDto;
import com.example.nvclothes.nvclothes.entity.products.TrainersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainersEntityMapper {

    TrainersEntityMapper INSTANCE = Mappers.getMapper(TrainersEntityMapper.class);

    @Mapping(source = "order", target = "orderDto")
    TrainersDto toDto(TrainersEntity trainersEntity);

    @Mapping(source = "order", target = "orderDto")
    TrainersEntity toEntity(TrainersDto  trainersDto);
}
