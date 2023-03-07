package com.example.nvclothes.nvclothes.service;

import com.example.nvclothes.nvclothes.dto.TrainersDto;
import com.example.nvclothes.nvclothes.entity.products.TrousersEntity;
import com.example.nvclothes.nvclothes.model.Attribute;
import com.example.nvclothes.nvclothes.repository.interfaces.TrousersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrousersEntityService {

    private final TrousersEntityRepositoryInterface trousersRepository;

    public void createHoodieEntity(@RequestBody TrousersEntity trousersDto) throws Exception{

        if (trousersDto.getCost() != null && trousersDto.getBrand()!=null
                && trousersDto.getName()!=null && trousersDto.getAmount()!=null){
            TrousersEntity trousersEntity = TrousersEntity.builder()
                    .attribute(Attribute.BRAND.toString()).value(trousersDto.getBrand()).build();
            trousersEntity.setAttribute(Attribute.NAME.toString());
            trousersEntity.setValue(trousersDto.getName());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.COST.toString());
            trousersEntity.setValue(trousersDto.getCost().toString());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.SIZE.toString());
            trousersEntity.setValue(trousersDto.getSize().toString());
            trousersRepository.save(trousersEntity);
            trousersEntity.setAttribute(Attribute.AMOUNT.toString());
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
        return trousersRepository.findAll();
    }

    public void deleteTrousersEntityById(Long id){
        trousersRepository.deleteTrousersEntityById(id);
    }
}
