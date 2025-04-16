package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.PaymentMethodDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.service.PaymentMethodService;
import com.example.finding_spare_part.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> getPaymentMethodsByUserId(@PathVariable Long userId) {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.getPaymentMethodsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", paymentMethods));
    }

    @GetMapping("/default/{userId}")
    public ResponseEntity<ResponseDTO> getDefaultPaymentMethod(@PathVariable Long userId) {
        PaymentMethodDTO defaultPaymentMethod = paymentMethodService.getDefaultPaymentMethod(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", defaultPaymentMethod));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> savePaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        PaymentMethodDTO savedPaymentMethod = paymentMethodService.savePaymentMethod(paymentMethodDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", savedPaymentMethod));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deletePaymentMethod(@PathVariable Long id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Payment method deleted successfully", null));
    }
}