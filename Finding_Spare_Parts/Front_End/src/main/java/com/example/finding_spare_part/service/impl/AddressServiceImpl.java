package com.example.finding_spare_part.service.impl;

import com.example.finding_spare_part.dto.AddressDTO;
import com.example.finding_spare_part.entity.Address;
import com.example.finding_spare_part.repo.AddressRepository;
import com.example.finding_spare_part.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddressesByUserId(Long userId) {
        try {
            List<Address> addresses = addressRepository.findByUserId(userId);
            return addresses.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving addresses: " + e.getMessage());
        }
    }

    @Override
    public AddressDTO getDefaultAddress(Long userId) {
        try {
            return addressRepository.findByUserIdAndIsDefaultTrue(userId)
                    .map(this::convertToDTO)
                    .orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving default address: " + e.getMessage());
        }
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO) {
        try {
            // If this address is set as default, unset any existing default address
            if (addressDTO.isDefault() && addressDTO.getUserId() != null) {
                addressRepository.findByUserIdAndIsDefaultTrue(addressDTO.getUserId())
                        .ifPresent(existingDefault -> {
                            existingDefault.setDefault(false);
                            addressRepository.save(existingDefault);
                        });
            }

            Address address = convertToEntity(addressDTO);
            Address savedAddress = addressRepository.save(address);
            return convertToDTO(savedAddress);
        } catch (Exception e) {
            throw new RuntimeException("Error saving address: " + e.getMessage());
        }
    }

    @Override
    public void deleteAddress(Long id) {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting address: " + e.getMessage());
        }
    }

    private AddressDTO convertToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setUserId(address.getUserId());
        dto.setFullName(address.getFullName());
        dto.setPhoneNumber(address.getPhoneNumber());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        dto.setDefault(address.isDefault());
        dto.setAddressType(address.getAddressType());
        return dto;
    }

    private Address convertToEntity(AddressDTO dto) {
        Address address = new Address();
        if (dto.getId() != null) {
            address.setId(dto.getId());
        }
        address.setUserId(dto.getUserId());
        address.setFullName(dto.getFullName());
        address.setPhoneNumber(dto.getPhoneNumber());
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setDefault(dto.isDefault());
        address.setAddressType(dto.getAddressType());
        return address;
    }
}