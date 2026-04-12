package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
