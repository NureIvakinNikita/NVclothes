package com.example.nvclothes.nvclothes.repository.interfaces;

import com.example.nvclothes.nvclothes.entity.products.TrousersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrousersEntityRepositoryInterface extends JpaRepository<TrousersEntity, Long> {
    Optional<TrousersEntity> getTrousersEntitiesById(@Param("id") Long id);

    Optional<TrousersEntity> getTrousersEntityByBrand(@Param("brand") String brand);
    List<TrousersEntity> getTrousersEntitiesByBrand(@Param("brand") String brand);

    Optional<TrousersEntity> getTrousersEntityByName(@Param("name") String name);
    List<TrousersEntity> getTrousersEntitiesByName(@Param("name") String name);

    void deleteTrousersEntityById(@Param("id") Long id);
}
