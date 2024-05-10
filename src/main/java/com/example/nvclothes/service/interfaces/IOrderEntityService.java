package com.example.nvclothes.service.interfaces;

import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.OrderEntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IOrderEntityService {

    void createOrder(OrderEntity order, List<Product> products, Long clientId, String recipient);

    OrderEntity getOrderEntityById(Long id) throws OrderEntityNotFoundException;

    OrderEntity getOrderEntityByClientIdAndOrderGroupId(Long clientId, Long orderGroupId) throws OrderEntityNotFoundException;

    List<OrderEntity> getOrderEntitiesByRegistrationDate(Date date);

    List<OrderEntity> getAllOrders();
}
