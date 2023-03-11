package com.example.nvclothes.service;

import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.HoodieEntity;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.model.Brand;
import com.example.nvclothes.model.ProductType;
import com.example.nvclothes.model.Size;
import com.example.nvclothes.repository.interfaces.TrainersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainersEntityService {

    private final TrainersEntityRepositoryInterface trainersRepository;

    public void createHoodieEntity(@RequestBody TrainersDto trainersDto) throws Exception{

        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TrainersEntity trainersEntity = TrainersEntity.builder()
                    .attribute(Attribute.BRAND.getDisplayName()).value(trainersDto.getBrand()).build();
            trainersEntity.setAttribute(Attribute.NAME.getDisplayName());
            trainersEntity.setValue(trainersDto.getName());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.COST.getDisplayName());
            trainersEntity.setValue(trainersDto.getCost().toString());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.SIZE.getDisplayName());
            trainersEntity.setValue(trainersDto.getSize().toString());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.AMOUNT.getDisplayName());
            trainersEntity.setValue(trainersDto.getAmount().toString());
            trainersRepository.save(trainersEntity);
        }
        else {
            throw new Exception();
        }

    }

    public TrainersEntity getTrainersEntityById(Long id){
        return trainersRepository.getTrainersEntitiesById(id).get();
    }

    public List<TrainersEntity> getTrainersEntitiesByBrand(String brand){
        return  trainersRepository.getTrainersEntitiesByBrand(brand);
    }

    public List<TrainersEntity> getAllTrainersEntities(){
        List<TrainersEntity> list = trainersRepository.findAll();
        Comparator<TrainersEntity> byId = Comparator.comparing(TrainersEntity::getId);
        Collections.sort(list, byId);
        List<TrainersEntity> entities = new ArrayList<>();
        TrainersEntity trainersEntity = TrainersEntity.builder().build();
        Long numericValues;
        for (TrainersEntity entity : list){
            switch (entity.getAttribute()){
                case "BRAND":
                    trainersEntity.setBrand(Brand.fromDisplayName(entity.getValue()));
                    break;
                case "NAME":
                    trainersEntity.setName(entity.getValue());
                    break;
                case "COST":
                    numericValues = Long.parseLong(entity.getValue().split("£")[0]);
                    trainersEntity.setCost(numericValues);
                    break;
                case "SIZE":
                    trainersEntity.setSize(Size.fromDisplayName(entity.getValue()));
                    break;
                case "AMOUNT":
                    numericValues = Long.parseLong(entity.getValue());
                    trainersEntity.setAmount(numericValues);
                    trainersEntity.setId(entity.getId());
                    trainersEntity.setTrainersId(entity.getTrainersId());
                    trainersEntity.setProductType(ProductType.TRAINERS);
                    entities.add(trainersEntity);
                    trainersEntity = trainersEntity.builder().build();
                    break;
                default:
                    break;
            }
        }
        return entities;
    }

    public void deleteTrainersEntityById(Long id){
        trainersRepository.deleteTrainersEntityById(id);
    }
}
