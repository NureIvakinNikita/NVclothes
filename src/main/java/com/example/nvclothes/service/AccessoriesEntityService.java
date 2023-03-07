package com.example.nvclothes.nvclothes.service;

import com.example.nvclothes.nvclothes.dto.AccessoriesDto;
import com.example.nvclothes.nvclothes.entity.products.AccessoriesEntity;
import com.example.nvclothes.nvclothes.model.Attribute;
import com.example.nvclothes.nvclothes.repository.interfaces.AccessoriesEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
                    .attribute(Attribute.BRAND.toString()).value(accessoriesDto.getBrand()).build();

            accessoriesEntity.setAccessoriesId(++count);
            accessoriesEntity.setAttribute(Attribute.NAME.toString());
            accessoriesEntity.setValue(accessoriesDto.getName());
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.COST.toString());
            accessoriesEntity.setValue(accessoriesDto.getCost().toString());
            accessoriesRepository.save(accessoriesEntity);
            accessoriesEntity.setAttribute(Attribute.AMOUNT.toString());
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
        return accessoriesRepository.findAll();
    }

    public void deleteAccessoryEntityById(Long id){
        accessoriesRepository.deleteAccessoriesEntityById(id);
    }
}
