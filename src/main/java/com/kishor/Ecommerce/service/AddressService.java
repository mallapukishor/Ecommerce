package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddress();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getUserAddress(User user);

    AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}
