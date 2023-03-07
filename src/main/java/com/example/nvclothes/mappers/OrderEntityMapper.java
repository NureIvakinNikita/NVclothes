package com.example.nvclothes.mappers;

import com.example.nvclothes.dto.OrderDto;
import com.example.nvclothes.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderEntityMapper {

    OrderEntityMapper INSTANCE = Mappers.getMapper(OrderEntityMapper.class);

    @Mapping(source = "trainers", target = "trainersDto")
    @Mapping(source = "trousers", target = "trousersDto")
    @Mapping(source = "hoodie", target = "hoodieDto")
    @Mapping(source = "TShirt", target = "TShirtDto")
    @Mapping(source = "accessories", target = "accessoriesDto")
    //@Mapping(source = "client", target = "clientDto")
    OrderDto toDto(OrderEntity orderEntity);
}
