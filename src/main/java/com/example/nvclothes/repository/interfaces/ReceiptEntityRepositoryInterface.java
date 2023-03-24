package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptEntityRepositoryInterface extends JpaRepository<ReceiptEntity, Long> {

    Optional<ReceiptEntity> getReceiptEntityById(Long id);

    Optional<ReceiptEntity> getReceiptEntityByOrderIdAndOrderGroupId(Long orderId, Long orderGroupId);

    List<ReceiptEntity> getReceiptEntitiesByPaymentDate(Date paymentDate);
}
