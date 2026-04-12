package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.exceptions.RescourceNotFoundException;
import com.kishor.Ecommerce.project.model.Address;
import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;
import com.kishor.Ecommerce.project.repository.AddressRepository;
import com.kishor.Ecommerce.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
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

    @Override
    public List<AddressDTO> getAddress() {
        List<Address>addresses=addressRepository.findAll();
      List<AddressDTO>addressDTOS=  addresses.stream().map(address ->modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
        return addressDTOS ;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address=addressRepository.findById(addressId)
                .orElseThrow(()->new RescourceNotFoundException("address","addressId",addressId));
        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddress(User user) {
        List<Address>addresses=user.getAddresses();
        return addresses.stream().map(address -> modelMapper.map(address,AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddressById(Long addressId, AddressDTO addressDTO) {
        Address addressFromDatabase=addressRepository.findById(addressId)
                .orElseThrow(()->new RescourceNotFoundException("address","addressId",addressId));
        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setPincode(addressDTO.getPincode());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuildingName(addressDTO.getBuildingName());

        Address updatedAddress=addressRepository.save(addressFromDatabase);
        User user=addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address address=addressRepository.findById(addressId)
                .orElseThrow(()->new RescourceNotFoundException("Address","AddressId",addressId));
        User user=address.getUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        userRepository.save(user);
        addressRepository.delete(address);
        return "Address  deleted successfully with address id"+addressId;
    }
}
