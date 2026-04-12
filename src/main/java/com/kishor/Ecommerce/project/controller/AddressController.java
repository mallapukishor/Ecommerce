package com.kishor.Ecommerce.project.controller;

import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;
import com.kishor.Ecommerce.project.utils.AuthUtil;
import com.kishor.Ecommerce.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
   private AddressService addressService;
    @Autowired
    private AuthUtil authUtil;
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO>createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user=authUtil.loggedInUser();
        AddressDTO savedAddress  =addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>>getAddress(){
       List<AddressDTO> addressDTO=addressService.getAddress();
       return new  ResponseEntity<>(addressDTO,HttpStatus.OK);
    }
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }
    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>>getUserAddress(){
        User user=authUtil.loggedInUser();
        List<AddressDTO>addressDTOS=addressService.getUserAddress(user);
        return new ResponseEntity<>(addressDTOS,HttpStatus.OK);

    }
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO>updateAddressById(@PathVariable Long addressId,@RequestBody AddressDTO addressDTO){
        AddressDTO addressDTOs=addressService.updateAddressById(addressId,addressDTO);
        return new ResponseEntity<>(addressDTOs,HttpStatus.OK);
    }
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String>deleteAddress(@PathVariable Long addressId){
        String status =addressService.deleteAddress(addressId);
        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
