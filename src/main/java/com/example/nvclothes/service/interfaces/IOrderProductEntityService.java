package com.example.nvclothes.service.interfaces;


import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.exception.OrderProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderProductEntityService {

    OrderProductEntity getOrderProductEntityByOrderGroupId(Long orderGroupId) throws OrderProductNotFoundException;

    List<OrderProductEntity> getOrderProductEntitiesByProductIdAndProductType(Long productId, String productType);

    void save(OrderProductEntity orderProductEntity);

    List<OrderProductEntity> findAll();
}
