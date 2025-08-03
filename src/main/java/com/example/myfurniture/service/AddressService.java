package com.example.myfurniture.service;

import com.example.myfurniture.dto.request.AddressCreationReq;
import com.example.myfurniture.dto.request.AddressUpdateReq;
import com.example.myfurniture.dto.response.AddressResponse;
import com.example.myfurniture.entity.Address;
import com.example.myfurniture.mapper.IAddressMapper;
import com.example.myfurniture.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    IAddressMapper addressMapper;

    public AddressResponse addNewAddress(Long userId, AddressCreationReq req) {
        return null;
    }

    public void deleteAddress(Long addressId) {
    }

    public AddressResponse updateAddress(Long addressId, AddressUpdateReq newAddress) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        addressMapper.updateAddress(address,newAddress);
        return addressMapper.toAddressResponse(addressRepository.save(address));
    }
}
