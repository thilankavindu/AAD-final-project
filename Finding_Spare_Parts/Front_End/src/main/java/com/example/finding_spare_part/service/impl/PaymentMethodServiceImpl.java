package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.PaymentMethodDTO;
import com.example.finding_spare_part.entity.PaymentMethod;
import com.example.finding_spare_part.repo.PaymentMethodRepository;
import com.example.finding_spare_part.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDTO> getPaymentMethodsByUserId(Long userId) {
        try {
            List<PaymentMethod> paymentMethods = paymentMethodRepository.findByUserId(userId);
            return paymentMethods.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving payment methods: " + e.getMessage());
        }
    }

    @Override
    public PaymentMethodDTO getDefaultPaymentMethod(Long userId) {
        try {
            return paymentMethodRepository.findByUserIdAndIsDefaultTrue(userId)
                    .map(this::convertToDTO)
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving default payment method: " + e.getMessage());
        }
    }

    @Override
    public PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        try {
            // If this payment method is set as default, unset any existing default
            if (paymentMethodDTO.isDefault() && paymentMethodDTO.getUserId() != null) {
                paymentMethodRepository.findByUserIdAndIsDefaultTrue(paymentMethodDTO.getUserId())
                        .ifPresent(existingDefault -> {
                            existingDefault.setDefault(false);
                            paymentMethodRepository.save(existingDefault);
                        });
            }

            PaymentMethod paymentMethod = convertToEntity(paymentMethodDTO);
            PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethod);
            return convertToDTO(savedPaymentMethod);
        } catch (Exception e) {
            throw new RuntimeException("Error saving payment method: " + e.getMessage());
        }
    }

    @Override
    public void deletePaymentMethod(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting payment method: " + e.getMessage());
        }
    }

    private PaymentMethodDTO convertToDTO(PaymentMethod paymentMethod) {
        PaymentMethodDTO dto = new PaymentMethodDTO();
        dto.setId(paymentMethod.getId());
        dto.setUserId(paymentMethod.getUserId());
        dto.setType(paymentMethod.getType());
        dto.setCardNumber(paymentMethod.getCardNumber());
        dto.setCardHolderName(paymentMethod.getCardHolderName());
        dto.setExpiryMonth(paymentMethod.getExpiryMonth());
        dto.setExpiryYear(paymentMethod.getExpiryYear());
        dto.setDefault(paymentMethod.isDefault());
        return dto;
    }

    private PaymentMethod convertToEntity(PaymentMethodDTO dto) {
        PaymentMethod paymentMethod = new PaymentMethod();
        if (dto.getId() != null) {
            paymentMethod.setId(dto.getId());
        }
        paymentMethod.setUserId(dto.getUserId());
        paymentMethod.setType(dto.getType());
        paymentMethod.setCardNumber(dto.getCardNumber());
        paymentMethod.setCardHolderName(dto.getCardHolderName());
        paymentMethod.setExpiryMonth(dto.getExpiryMonth());
        paymentMethod.setExpiryYear(dto.getExpiryYear());
        paymentMethod.setDefault(dto.isDefault());
        return paymentMethod;
    }
}