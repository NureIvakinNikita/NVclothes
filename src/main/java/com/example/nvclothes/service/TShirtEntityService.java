package com.example.nvclothes.service;

import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.TShirtEntity;
import com.example.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.repository.interfaces.TShirtEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TShirtEntityService {
    private final TShirtEntityRepositoryInterface tShirtRepository;

    public void createHoodieEntity(@RequestBody TrainersDto trainersDto) throws Exception{

        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TShirtEntity tShirtEntity = TShirtEntity.builder()
                    .attribute(Attribute.BRAND.toString()).value(trainersDto.getBrand()).build();
            tShirtEntity.setAttribute(Attribute.NAME.toString());
            tShirtEntity.setValue(trainersDto.getName());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.COST.toString());
            tShirtEntity.setValue(trainersDto.getCost().toString());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.SIZE.toString());
            tShirtEntity.setValue(trainersDto.getSize().toString());
            tShirtRepository.save(tShirtEntity);
            tShirtEntity.setAttribute(Attribute.AMOUNT.toString());
            tShirtEntity.setValue(trainersDto.getAmount().toString());
            tShirtRepository.save(tShirtEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TShirtEntity getTShirtEntityById(Long id){
        return tShirtRepository.getTShirtEntitiesById(id).get();
    }

    public List<TShirtEntity> getTShirtEntitiesByBrand(String brand){
        return  tShirtRepository.getTShirtEntitiesByBrand(brand);
    }

    public List<TShirtEntity> getAllTrousersEntities(){
        List<TShirtEntity> list = tShirtRepository.findAll();
        Comparator<TShirtEntity> byId = Comparator.comparing(TShirtEntity::getId);
        Collections.sort(list, byId);
        List<TShirtEntity> entities = new ArrayList<>();
        TShirtEntity tShirtEntity = TShirtEntity.builder().build();
        Long numericValues;
        for (TShirtEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    tShirtEntity.setBrand(entity.getValue());
                    break;
                case "NAME":
                    tShirtEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    tShirtEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    tShirtEntity.setSize(entity.getSize());
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    tShirtEntity.setAmount(numericValues);
                    tShirtEntity.setId(entity.getId());
                    tShirtEntity.setTShirtId(entity.getTShirtId());
                    tShirtEntity.setProductType(ProductType.TSHIRT);
                    entities.add(tShirtEntity);
                    tShirtEntity = tShirtEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public void deleteTShirtEntityById(Long id){
        tShirtRepository.deleteTShirtEntityById(id);
    }
}
