package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.payload.CaterogyDTO;
import com.kishor.Ecommerce.project.payload.CaterogyResponse;

import java.util.ArrayList;
import java.util.List;

public interface ServiceContro {
    CaterogyResponse getCaterogies(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    CaterogyDTO createCaterogy(CaterogyDTO caterogyDTO);
    CaterogyDTO deleteCaterogy(Long caterogyID);
    CaterogyDTO updateCaterogy(CaterogyDTO caterogyDTO, Long caterogyID);
}
