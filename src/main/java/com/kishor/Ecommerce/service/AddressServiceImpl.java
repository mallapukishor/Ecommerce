package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.model.Address;
import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;
import com.kishor.Ecommerce.project.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepository addressRepository;
    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address=modelMapper.map(addressDTO, Address.class);

        List<Address>addressesList=user.getAddresses();
        addressesList.add(address);
        user.setAddresses(addressesList);
        address.setUser(user);
        Address savedAddress=addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }
}
