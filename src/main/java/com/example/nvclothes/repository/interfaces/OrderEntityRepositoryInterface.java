package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.OrderEntity;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderEntityRepositoryInterface extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> getOrderEntityById(Long id);

    List<OrderEntity> findAll();

    //List<OrderEntity> getOrderEntitiesByProductType(String productType);

    List<OrderEntity> getOrderEntitiesByRegistrationDate(Date date);

    Optional<OrderEntity> getOrderEntityByClientIdAndOrderGroupId(Long clientId, Long orderGroupId);
}
