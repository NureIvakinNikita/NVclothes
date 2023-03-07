package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.products.AccessoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoriesEntityRepositoryInterface extends JpaRepository<AccessoriesEntity, Long> {
    Optional<AccessoriesEntity> getAccessoriesEntitiesById(@Param("id") Long id);

    Optional<AccessoriesEntity> getAccessoriesEntityByBrand(@Param("brand") String brand);
    List<AccessoriesEntity> getAccessoriesEntitiesByBrand(@Param("brand") String brand);

    Optional<AccessoriesEntity> getAccessoriesEntityByName(@Param("name") String name);
    List<AccessoriesEntity> getAccessoriesEntitiesByName(@Param("name") String name);
    void deleteAccessoriesEntityById(@Param("id") Long id);
}
