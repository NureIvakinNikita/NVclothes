package com.example.nvclothes.mappers;

import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.TrainersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainersEntityMapper {

    TrainersEntityMapper INSTANCE = Mappers.getMapper(TrainersEntityMapper.class);

    @Mapping(target = "orderDto", source = "trainersEntity.order")
    TrainersDto toDto(TrainersEntity trainersEntity);

    @Mapping(target = "order", source = "trainersDto.orderDto")
    TrainersEntity toEntity(TrainersDto  trainersDto);
}
