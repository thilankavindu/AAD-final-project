package com.example.finding_spare_part.service;

import com.example.finding_spare_part.dto.AddressDTO;
import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddressesByUserId(Long userId);
    AddressDTO getDefaultAddress(Long userId);
    AddressDTO saveAddress(AddressDTO addressDTO);
    void deleteAddress(Long id);
}
