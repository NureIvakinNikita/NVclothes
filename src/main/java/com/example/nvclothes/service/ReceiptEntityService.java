package com.example.nvclothes.service;


import com.example.nvclothes.entity.OrderEntity;
import com.example.nvclothes.entity.ReceiptEntity;
import com.example.nvclothes.exception.OrderEntityNotFoundException;
import com.example.nvclothes.exception.ReceiptEntityNotFoundException;
import com.example.nvclothes.repository.interfaces.ReceiptEntityRepositoryInterface;
import jakarta.persistence.criteria.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReceiptEntityService {

    @Autowired
    private ReceiptEntityRepositoryInterface receiptEntityRepository;

    public ReceiptEntity getReceiptEntityById(Long id) throws ReceiptEntityNotFoundException{
        Optional<ReceiptEntity> receiptOptional = receiptEntityRepository.getReceiptEntityById(id);
        if (!receiptOptional.isPresent()) {
            throw new ReceiptEntityNotFoundException("Receipt not found for id: " + id);
        }
        return receiptOptional.get();

    }

    public ReceiptEntity getReceiptEntityByOrderIdAndOrderGroupId(Long orderId, Long orderGroupId) throws ReceiptEntityNotFoundException{
        Optional<ReceiptEntity> receiptOptional = receiptEntityRepository.getReceiptEntityByOrderIdAndOrderGroupId(orderId, orderGroupId);
        if (!receiptOptional.isPresent()) {
            throw new ReceiptEntityNotFoundException("Receipt not found for order id: " + orderId + "and order group id: " + orderGroupId);
        }
        return receiptOptional.get();
    }

    public void save(ReceiptEntity receipt){
        receiptEntityRepository.save(receipt);
    }

    public List<ReceiptEntity> getReceiptEntitiesByPaymentDate(Date paymentDate){
        return receiptEntityRepository.getReceiptEntitiesByPaymentDate(paymentDate);
    }
}
