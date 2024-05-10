package com.example.nvclothes.mappers;


import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.dto.ClientDto;
import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientEntityMapper {

    ClientEntityMapper INSTANCE = Mappers.getMapper(ClientEntityMapper.class);


    ClientDto toDto(ClientEntity clientEntity);

    ClientEntity toEntity(ClientDto clientDto);

}
