package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductEntityRepositoryInterface extends JpaRepository<OrderProductEntity, Long> {

    Optional<OrderProductEntity> getOrderProductEntityByOrderGroupId(Long orderGroupId);

    List<OrderProductEntity> getOrderProductEntitiesByOrderGroupId(Long orderGroupId);

    List<OrderProductEntity> getOrderProductEntitiesByProductIdAndProductType(Long productId, String productType);
}
