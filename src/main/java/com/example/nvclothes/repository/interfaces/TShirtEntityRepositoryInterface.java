package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.products.TShirtEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TShirtEntityRepositoryInterface extends JpaRepository<TShirtEntity, Long> {
    List<TShirtEntity> getTShirtEntitiesByProductId(@Param("t_shirt_id") Long id);

    Optional<TShirtEntity> getTShirtEntityByBrand(@Param("brand") String brand);
    List<TShirtEntity> getTShirtEntitiesByBrand(@Param("brand") String brand);

    Optional<TShirtEntity> getTShirtEntityByProductIdAndAttribute(@Param("t_shirt_id") Long productId, @Param("attribute") String attribute);
    Optional<TShirtEntity> getTShirtEntityByName(@Param("name") String name);
    List<TShirtEntity> getTShirtEntitiesByName(@Param("name") String name);

    @Transactional
    void deleteTShirtEntitiesByProductId(@Param("t_shirt_id") Long productId);
    @Transactional
    void deleteTShirtEntityById(@Param("id") Long id);
}
