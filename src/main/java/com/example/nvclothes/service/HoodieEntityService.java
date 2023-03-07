package com.example.nvclothes.nvclothes.service;

import com.example.nvclothes.nvclothes.dto.HoodieDto;
import com.example.nvclothes.nvclothes.entity.products.HoodieEntity;
import com.example.nvclothes.nvclothes.model.Attribute;
import com.example.nvclothes.nvclothes.repository.interfaces.HoodieEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        return hoodieRepository.findAll();
    }

    public void deleteHoodieEntityById(Long id){
        hoodieRepository.deleteHoodieEntityById(id);
    }
}
