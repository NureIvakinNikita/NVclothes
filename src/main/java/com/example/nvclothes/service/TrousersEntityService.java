package com.example.nvclothes.service;

import com.example.nvclothes.dto.TrousersDto;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.TrousersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrousersEntityService {

    private final TrousersEntityRepositoryInterface trousersRepository;

    public void createHoodieEntity(@RequestBody TrousersDto trousersDto) throws Exception{

        if (trousersDto.getCost() != null && trousersDto.getBrand()!=null
                && trousersDto.getName()!=null && trousersDto.getAmount()!=null){
            TrousersEntity trousersEntity = TrousersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trousersDto.getBrand()).build();
            trousersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trousersEntity.setValue(trousersDto.getName());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.COST.getDisplayName());
            trousersEntity.setValue(trousersDto.getCost().toString());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trousersEntity.setValue(trousersDto.getSize().toString());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trousersEntity.setValue(trousersDto.getAmount().toString());
            trousersRepository.save(trousersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrousersEntity getTrousersEntityById(Long id){
        return trousersRepository.getTrousersEntitiesById(id).get();
    }

    public List<TrousersEntity> getTrousersEntitiesByBrand(String brand){
        return  trousersRepository.getTrousersEntitiesByBrand(brand);
    }

    public List<TrousersEntity> getAllTrousersEntities(){
        List<TrousersEntity> list = trousersRepository.findAll();
        Comparator<TrousersEntity> byId = Comparator.comparing(TrousersEntity::getId);
        Collections.sort(list, byId);
        List<TrousersEntity> entities = new ArrayList<>();
        TrousersEntity trousersEntity = TrousersEntity.builder().build();
        Long numericValues;
        for (TrousersEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    trousersEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    trousersEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    trousersEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    trousersEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    trousersEntity.setAmount(numericValues);
                    trousersEntity.setId(entity.getId());
                    trousersEntity.setTrousersId(entity.getTrousersId());
                    trousersEntity.setProductType(ProductType.TROUSERS);
                    entities.add(trousersEntity);
                    trousersEntity = trousersEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public void deleteTrousersEntityById(Long id){
        trousersRepository.deleteTrousersEntityById(id);
    }
}
