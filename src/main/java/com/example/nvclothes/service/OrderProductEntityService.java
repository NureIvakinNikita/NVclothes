package com.example.nvclothes.service;

import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.exception.OrderEntityNotFoundException;
import com.example.nvclothes.exception.OrderProductNotFoundException;
import com.example.nvclothes.repository.interfaces.OrderProductEntityRepositoryInterface;
import com.example.nvclothes.service.interfaces.IOrderEntityService;
import com.example.nvclothes.service.interfaces.IOrderProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
public class OrderProductEntityService implements IOrderProductEntityService {

    @Autowired
    private OrderProductEntityRepositoryInterface orderProductEntityRepository;

    public OrderProductEntity getOrderProductEntityByOrderGroupId(Long orderGroupId) throws OrderProductNotFoundException{
        Optional<OrderProductEntity> orderProductOptional = orderProductEntityRepository.getOrderProductEntityByOrderGroupId(orderGroupId);
        if (!orderProductOptional.isPresent()) {
            throw new OrderProductNotFoundException("OrderProductEntity was not found for id: " + orderGroupId);
        }
        return orderProductOptional.get();
    }

    public List<OrderProductEntity> getOrderProductEntitiesByProductIdAndProductType(Long productId, String productType){
        List<OrderProductEntity> orderProductEntities = orderProductEntityRepository.getOrderProductEntitiesByProductIdAndProductType(productId, productType);
        return orderProductEntities;
    }

    public void save(OrderProductEntity orderProductEntity){
        orderProductEntityRepository.save(orderProductEntity);
    }

    public List<OrderProductEntity> findAll(){
        return orderProductEntityRepository.findAll();
    }
}
