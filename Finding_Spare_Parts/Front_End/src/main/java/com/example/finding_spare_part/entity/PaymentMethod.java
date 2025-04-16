package com.example.finding_spare_part.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_methods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String type; // "CREDIT_CARD", "PAYPAL", etc.
    private String cardNumber; // Last 4 digits for security
    private String cardHolderName;
    private String expiryMonth;
    private String expiryYear;
    private boolean isDefault;
}