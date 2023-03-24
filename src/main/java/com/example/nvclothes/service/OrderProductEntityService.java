package com.example.nvclothes.service;

import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.exception.OrderEntityNotFoundException;
import com.example.nvclothes.exception.OrderProductNotFoundException;
import com.example.nvclothes.repository.interfaces.OrderProductEntityRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductEntityService {

    @Autowired
    private OrderProductEntityRepositoryInterface orderProductEntityRepository;

    OrderProductEntity getOrderProductEntityByOrderGroupId(Long orderGroupId) throws OrderProductNotFoundException{
        Optional<OrderProductEntity> orderProductOptional = orderProductEntityRepository.getOrderProductEntityByOrderGroupId(orderGroupId);
        if (!orderProductOptional.isPresent()) {
            throw new OrderProductNotFoundException("OrderProductEntity was not found for id: " + orderGroupId);
        }
        return orderProductOptional.get();
    }

    List<OrderProductEntity> getOrderProductEntitiesByProductIdAndProductType(Long productId, String productType){
        List<OrderProductEntity> orderProductEntities = orderProductEntityRepository.getOrderProductEntitiesByProductIdAndProductType(productId, productType);
        return orderProductEntities;
    }

    public void save(OrderProductEntity orderProductEntity){
        orderProductEntityRepository.save(orderProductEntity);
    }

}
