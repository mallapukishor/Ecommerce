package com.kishor.Ecommerce.project.controller;

import com.kishor.Ecommerce.project.config.AppConstant;
import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.payload.CaterogyDTO;
import com.kishor.Ecommerce.project.payload.CaterogyResponse;
import com.kishor.Ecommerce.service.ServiceContro;
import com.kishor.Ecommerce.service.ServiceController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CaterogyController {
    @Autowired
    private ServiceContro serviceContro;


    public CaterogyController(ServiceContro serviceContro){
        this.serviceContro=serviceContro;
    }

    @GetMapping("/public/caterogies")
    public ResponseEntity<CaterogyResponse>getCaterogies(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
                                                         @RequestParam( name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
                                                         @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY,required = false)String sortBy,
                                                         @RequestParam(name="sortOrder",defaultValue = AppConstant.SORT_DIR)String sortOrder){
        CaterogyResponse caterogyResponse = serviceContro.getCaterogies(pageNumber,pageSize,sortBy,sortOrder);
        return  new ResponseEntity<>( caterogyResponse,HttpStatus.OK );

    }
    @PostMapping("/public/caterogies")
    public ResponseEntity<CaterogyDTO> createCaterogy(@Valid @RequestBody CaterogyDTO caterogyDTO){
        serviceContro.createCaterogy(caterogyDTO);
        return new ResponseEntity<CaterogyDTO>( caterogyDTO, HttpStatus.CREATED);
    }
    @PutMapping("/public/caterogies/{caterogyID}")
    public ResponseEntity< CaterogyDTO> updateCaterogy( @Valid @RequestBody CaterogyDTO caterogyDTO,
                                                  @PathVariable long caterogyID ){
            CaterogyDTO saveCaterogy=serviceContro.updateCaterogy(caterogyDTO,caterogyID);
            return new ResponseEntity<>( caterogyDTO, HttpStatus.OK);


    }
    @DeleteMapping("/public/caterogies/{caterogyID}")
    public ResponseEntity<CaterogyDTO> deleteCaterogy( @PathVariable  long caterogyID){
        CaterogyDTO deleteCaterogy =serviceContro.deleteCaterogy(caterogyID);
        return new ResponseEntity<>(deleteCaterogy, HttpStatus.OK);
    }

}


