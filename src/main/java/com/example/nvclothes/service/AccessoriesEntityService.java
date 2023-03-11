package com.example.nvclothes.service;

import com.example.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessoriesEntityService {

    private final AccessoriesEntityRepositoryInterface accessoriesRepository;



    public void createAccessoryEntity(@RequestBody AccessoriesDto accessoriesDto) throws Exception{
        Long count;
        if(accessoriesRepository.findAll().size()>0) count = accessoriesRepository.findAll().get(accessoriesRepository.findAll().size()-1).getAccessoriesId();
        else count = 0L;
        if (accessoriesDto.getCost() != null && accessoriesDto.getBrand()!=null
                && accessoriesDto.getName()!=null && accessoriesDto.getAmount()!=null){
            AccessoriesEntity accessoriesEntity = AccessoriesEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(accessoriesDto.getBrand()).build();

            accessoriesEntity.setAccessoriesId(++count);
            accessoriesEntity.setAttribute(Attribute.NAME.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getName());
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.COST.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getCost().toString());
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            accessoriesEntity.setValue(accessoriesDto.getAmount().toString());
            accessoriesRepository.save(accessoriesEntity);

        }
        else {
            throw new Exception();
        }

    }

    public AccessoriesEntity getAccessoriesEntityById(Long id){
        return accessoriesRepository.getAccessoriesEntitiesById(id).get();
    }

    public List<AccessoriesEntity> getAccessoriesEntitiesByBrand(String brand){
        return  accessoriesRepository.getAccessoriesEntitiesByBrand(brand);
    }

    public List<AccessoriesEntity> getAllAccessoriesEntities(){
        List<AccessoriesEntity> list = accessoriesRepository.findAll();
        Comparator<AccessoriesEntity> byId = Comparator.comparing(AccessoriesEntity::getId);
        Collections.sort(list, byId);
        List<AccessoriesEntity> entities = new ArrayList<>();
        AccessoriesEntity accessoriesEntity = AccessoriesEntity.builder().build();
        Long numericValues;
        for (AccessoriesEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    accessoriesEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    accessoriesEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    accessoriesEntity.setCost(numericValues);
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    accessoriesEntity.setAmount(numericValues);
                    accessoriesEntity.setId(entity.getId());
                    accessoriesEntity.setAccessoriesId(entity.getAccessoriesId());
                    accessoriesEntity.setProductType(ProductType.ACCESSORY);
                    entities.add(accessoriesEntity);

                    accessoriesEntity = AccessoriesEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public void deleteAccessoryEntityById(Long id){
        accessoriesRepository.deleteAccessoriesEntityById(id);
    }
}
