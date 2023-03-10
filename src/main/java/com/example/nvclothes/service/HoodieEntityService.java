package com.example.nvclothes.service;

import com.example.nvclothes.dto.HoodieDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.entity.products.HoodieEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.repository.interfaces.HoodieEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoodieEntityService {
    private final HoodieEntityRepositoryInterface hoodieRepository;

    public void createHoodieEntity(@RequestBody HoodieDto hoodieDto) throws Exception{

        if (hoodieDto.getCost() != null && hoodieDto.getBrand()!=null
                && hoodieDto.getName()!=null && hoodieDto.getAmount()!=null){
            HoodieEntity hoodieEntity = HoodieEntity.builder()
                    .attribute(Attribute.BRAND.toString()).value(hoodieDto.getBrand()).build();
            hoodieEntity.setAttribute(Attribute.NAME.toString());
            hoodieEntity.setValue(hoodieDto.getName());
            hoodieRepository.save(hoodieEntity);
            hoodieEntity.setAttribute(Attribute.COST.toString());
            hoodieEntity.setValue(hoodieDto.getCost().toString());
            hoodieRepository.save(hoodieEntity);
            hoodieEntity.setAttribute(Attribute.SIZE.toString());
            hoodieEntity.setValue(hoodieDto.getSize().toString());
            hoodieRepository.save(hoodieEntity);
            hoodieEntity.setAttribute(Attribute.AMOUNT.toString());
            hoodieEntity.setValue(hoodieDto.getAmount().toString());
            hoodieRepository.save(hoodieEntity);
        }
        else {
            throw new Exception();
        }

    }

    public HoodieEntity getHoodieEntityById(Long id){
        return hoodieRepository.getHoodieEntitiesById(id).get();
    }

    public List<HoodieEntity> getHoodieEntitiesByBrand(String brand){
        return  hoodieRepository.getHoodieEntitiesByBrand(brand);
    }

    public List<HoodieEntity> getAllHoodieEntities(){
        List<HoodieEntity> list = hoodieRepository.findAll();
        Comparator<HoodieEntity> byId = Comparator.comparing(HoodieEntity::getId);
        Collections.sort(list, byId);
        List<HoodieEntity> entities = new ArrayList<>();
        HoodieEntity hoodieEntity = HoodieEntity.builder().build();
        Long numericValues;
        for (HoodieEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    hoodieEntity.setBrand(entity.getValue());
                    break;
                case "NAME":
                    hoodieEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    hoodieEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    hoodieEntity.setSize(entity.getSize());
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    hoodieEntity.setAmount(numericValues);
                    hoodieEntity.setId(entity.getId());
                    hoodieEntity.setHoodieId(entity.getHoodieId());
                    hoodieEntity.setProductType(ProductType.HOODIE);
                    entities.add(hoodieEntity);
                    hoodieEntity = HoodieEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public void deleteHoodieEntityById(Long id){
        hoodieRepository.deleteHoodieEntityById(id);
    }
}
