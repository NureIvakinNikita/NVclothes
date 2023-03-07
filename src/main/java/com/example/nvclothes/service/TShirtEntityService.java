package com.example.nvclothes.service;

import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.TShirtEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.repository.interfaces.TShirtEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        return tShirtRepository.findAll();
    }

    public void deleteTShirtEntityById(Long id){
        tShirtRepository.deleteTShirtEntityById(id);
    }
}
