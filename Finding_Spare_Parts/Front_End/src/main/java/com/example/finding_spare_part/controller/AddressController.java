package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.AddressDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.service.AddressService;
import com.example.finding_spare_part.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDTO> getAddressesByUserId(@PathVariable Long userId) {
        List<AddressDTO> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", addresses));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> saveAddress(@RequestBody AddressDTO addressDTO) {
        AddressDTO savedAddress = addressService.saveAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Success", savedAddress));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Address deleted successfully", null));
    }
}

