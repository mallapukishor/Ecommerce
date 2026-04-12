package com.kishor.Ecommerce.project.controller;

import com.kishor.Ecommerce.project.model.User;
import com.kishor.Ecommerce.project.payload.AddressDTO;
import com.kishor.Ecommerce.project.utils.AuthUtil;
import com.kishor.Ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
   private AddressService addressService;
    @Autowired
    private AuthUtil authUtil;
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO>createAddress(AddressDTO addressDTO){
        User user=authUtil.loggedInUser();
        AddressDTO savedAddress  =addressService.createAddress(addressDTO,user);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }
}
