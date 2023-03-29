package com.example.nvclothes.service;

import com.example.nvclothes.entity.ClientEntity;
import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.OrderProductEntity;
import com.example.nvclothes.entity.ReceiptEntity;
import com.example.nvclothes.entity.products.Product;
import com.example.nvclothes.exception.ClientNotFoundException;
import com.example.nvclothes.exception.OrderEntityNotFoundException;
import com.example.nvclothes.exception.ReceiptEntityNotFoundException;
import com.example.nvclothes.repository.interfaces.OrderEntityRepositoryInterface;
import com.example.nvclothes.repository.interfaces.OrderProductEntityRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderEntityService {

    @Autowired
    private OrderEntityRepositoryInterface orderEntityRepository;

    @Autowired
    private OrderProductEntityService orderProductEntityService;

    @Autowired
    private ClientEntityService clientEntityService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ReceiptEntityService receiptEntityService;

    public void createOrder(OrderEntity order, List<Product> products,Long clientId, String recipient){
        List<OrderEntity> orders = orderEntityRepository.findAll();
        Long orderGroupId = 1L;
        if (orders != null && orders.size() != 0){
            orderGroupId = orders.get(orders.size()-1).getOrderGroupId();
            orderGroupId++;
            order.setOrderGroupId(orderGroupId);
            order.setClientId(clientId);
        } else {
            order.setOrderGroupId(orderGroupId);
            order.setClientId(clientId);
        }

        orderEntityRepository.save(order);
        OrderEntity orderEntity;
        try {
            orderEntity = this.getOrderEntityByClientIdAndOrderGroupId(clientId, orderGroupId);
        } catch (OrderEntityNotFoundException e){
            log.info(e.toString());
            orderEntity = null;
        }
        OrderProductEntity orderProduct = OrderProductEntity.builder().build();

        Long sum = 0L;
        for (Product product : products){
            orderProduct = OrderProductEntity.builder()
                    .orderGroupId(orderEntity.getOrderGroupId())
                    .orderId(orderEntity.getId())
                    .productId(product.getProductId())
                    .productType(product.getProductType().toString())
                    .build();
            orderProductEntityService.save(orderProduct);
            sum = sum + product.getCost();
        }
        ReceiptEntity receipt = ReceiptEntity.builder()
                .address(order.getAddress())
                .money(sum)
                .recipient(recipient)
                .orderId(order.getId())
                .orderGroupId(order.getOrderGroupId())
                .paymentDate(new Date()).build();
        receiptEntityService.save(receipt);
        try {
            receipt = receiptEntityService.getReceiptEntityByOrderIdAndOrderGroupId(order.getId(), order.getOrderGroupId());
        } catch (ReceiptEntityNotFoundException e){
            log.info(e.toString());
        }
        order.setReceipt(receipt);
        orderEntityRepository.save(order);

        try {
            ClientEntity client = clientEntityService.getClientById(clientId);
            String text = client.getName() + " " + client.getLastName()+", your order was created and sent for processing. You will be called later to clarify the information provided. " +
                    "Thank you for using our website to shop for clothing!";
                    emailSenderService.sendSimpleEmail(client.getEmail(), "Order", text);
        } catch (ClientNotFoundException e){
            log.info(e.toString());
        }
    }

    public OrderEntity getOrderEntityById(Long id) throws OrderEntityNotFoundException{
        Optional<OrderEntity> orderOptional = orderEntityRepository.getOrderEntityById(id);
        if (!orderOptional.isPresent()) {
            throw new OrderEntityNotFoundException("Order not found for id: " + id);
        }
        return orderOptional.get();
    }

    public OrderEntity getOrderEntityByClientIdAndOrderGroupId(Long clientId, Long orderGroupId) throws OrderEntityNotFoundException{
        Optional<OrderEntity> orderOptional = orderEntityRepository.getOrderEntityByClientIdAndOrderGroupId(clientId, orderGroupId);
        if (!orderOptional.isPresent()) {
            throw new OrderEntityNotFoundException("Order not found for clientId: " + clientId + " and orderGroupId: " + orderGroupId);
        }
        return orderOptional.get();
    }

    /*public List<OrderEntity> getOrderEntitiesByProductType(String productType){
        return orderEntityRepository.getOrderEntitiesByProductType(productType);
    }*/

    public List<OrderEntity> getOrderEntitiesByRegistrationDate(Date date){
        return orderEntityRepository.getOrderEntitiesByRegistrationDate(date);
    }

}
