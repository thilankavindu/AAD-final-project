package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.PaymentMethodDTO;
import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDTO> getPaymentMethodsByUserId(Long userId);
    PaymentMethodDTO getDefaultPaymentMethod(Long userId);
    PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO);
    void deletePaymentMethod(Long id);
}