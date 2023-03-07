package com.example.nvclothes.nvclothes.repository.interfaces;

import com.example.nvclothes.nvclothes.entity.products.TrainersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainersEntityRepositoryInterface extends JpaRepository<TrainersEntity, Long> {

    Optional<TrainersEntity> getTrainersEntitiesById(@Param("id") Long id);

    Optional<TrainersEntity> getTrainersEntityByBrand(@Param("brand") String brand);
    List<TrainersEntity> getTrainersEntitiesByBrand(@Param("brand") String brand);

    Optional<TrainersEntity> getTrainersEntityByName(@Param("name") String name);
    List<TrainersEntity> getTrainersEntitiesByName(@Param("name") String name);

    void deleteTrainersEntityById(@Param("id") Long id);
}
