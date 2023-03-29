package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.products.TrousersEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrousersEntityRepositoryInterface extends JpaRepository<TrousersEntity, Long> {
    List<TrousersEntity> getTrousersEntitiesByProductId(@Param("trousers_id") Long id);

    Optional<TrousersEntity> getTrousersEntityByBrand(@Param("brand") String brand);
    List<TrousersEntity> getTrousersEntitiesByBrand(@Param("brand") String brand);

    Optional<TrousersEntity> getTrousersEntityByProductIdAndAttribute(@Param("trousers_id") Long productId, @Param("attribute") String attribute);

    Optional<TrousersEntity> getTrousersEntityByName(@Param("name") String name);
    List<TrousersEntity> getTrousersEntitiesByName(@Param("name") String name);

    @Transactional
    void deleteTrousersEntitiesByProductId(@Param("trouser_id") Long productId);
    @Transactional
    void deleteTrousersEntityById(@Param("id") Long id);
}
