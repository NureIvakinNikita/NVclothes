package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.products.TShirtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TShirtEntityRepositoryInterface extends JpaRepository<TShirtEntity, Long> {
    Optional<TShirtEntity> getTShirtEntitiesById(@Param("id") Long id);

    Optional<TShirtEntity> getTShirtEntityByBrand(@Param("brand") String brand);
    List<TShirtEntity> getTShirtEntitiesByBrand(@Param("brand") String brand);

    Optional<TShirtEntity> getTShirtEntityByName(@Param("name") String name);
    List<TShirtEntity> getTShirtEntitiesByName(@Param("name") String name);

    void deleteTShirtEntityById(@Param("id") Long id);
}
