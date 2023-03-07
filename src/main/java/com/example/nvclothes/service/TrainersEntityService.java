package com.example.nvclothes.service;

import com.example.nvclothes.dto.TrainersDto;
import com.example.nvclothes.entity.products.TrainersEntity;
import com.example.nvclothes.model.Attribute;
import com.example.nvclothes.repository.interfaces.TrainersEntityRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainersEntityService {

    private final TrainersEntityRepositoryInterface trainersRepository;

    public void createHoodieEntity(@RequestBody TrainersDto trainersDto) throws Exception{

        if (trainersDto.getCost() != null && trainersDto.getBrand()!=null
                && trainersDto.getName()!=null && trainersDto.getAmount()!=null){
            TrainersEntity trainersEntity = TrainersEntity.builder()
                    .attribute(Attribute.BRAND.toString()).value(trainersDto.getBrand()).build();
            trainersEntity.setAttribute(Attribute.NAME.toString());
            trainersEntity.setValue(trainersDto.getName());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.COST.toString());
            trainersEntity.setValue(trainersDto.getCost().toString());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.SIZE.toString());
            trainersEntity.setValue(trainersDto.getSize().toString());
            trainersRepository.save(trainersEntity);
            trainersEntity.setAttribute(Attribute.AMOUNT.toString());
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
        return trainersRepository.findAll();
    }

    public void deleteTrainersEntityById(Long id){
        trainersRepository.deleteTrainersEntityById(id);
    }
}
