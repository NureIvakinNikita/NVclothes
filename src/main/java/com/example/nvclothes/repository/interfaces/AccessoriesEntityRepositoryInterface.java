package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.products.AccessoriesEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessoriesEntityRepositoryInterface extends JpaRepository<AccessoriesEntity, Long> {
    List<AccessoriesEntity> getAccessoriesEntitiesByProductId (@Param("accessories_id") Long id);

    Optional<AccessoriesEntity> getAccessoriesEntityByBrand(@Param("brand") String brand);
    List<AccessoriesEntity> getAccessoriesEntitiesByBrand(@Param("brand") String brand);


    ///Optional<AccessoriesEntity> getAccessoriesEntityByProductIdAndBrand(@Param("accessories_id") Long ID, @Param(""))

    Optional<AccessoriesEntity> getAccessoriesEntityByProductIdAndAttribute(@Param("accessories_id") Long id,@Param("attribute") String attribute);

    Optional<AccessoriesEntity> getAccessoriesEntityByProductIdAndAttributeAndId(@Param("accessories_id") Long productId, @Param("attribute") String attribute, @Param("id") Long id);
    Optional<AccessoriesEntity> getAccessoriesEntityByName(@Param("name") String name);
    List<AccessoriesEntity> getAccessoriesEntitiesByName(@Param("name") String name);
    @Transactional
    void deleteAccessoriesEntitiesByProductId(@Param("accessories_id") Long productId);
    @Transactional
    void deleteAccessoriesEntityById(@Param("id") Long id);
}
