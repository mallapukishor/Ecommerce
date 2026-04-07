package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.exceptions.ApiException;
import com.kishor.Ecommerce.project.exceptions.RescourceNotFoundException;
import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.payload.CaterogyDTO;
import com.kishor.Ecommerce.project.payload.CaterogyResponse;
import com.kishor.Ecommerce.project.repository.CaterogyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceController implements ServiceContro{
    @Autowired
    private CaterogyRepository caterogyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CaterogyResponse getCaterogies(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Caterogy>caterogyPage=caterogyRepository.findAll(pageDetails);

        List<Caterogy>caterogies=caterogyPage.getContent();
        if(caterogies.isEmpty())
            throw  new ApiException("No Caterogy there");
        List<CaterogyDTO>caterogyDTOS=caterogies.stream()
                .map(caterogy -> modelMapper.map(caterogy, CaterogyDTO.class))
                .toList();
        CaterogyResponse caterogyResponse=new CaterogyResponse();
        caterogyResponse.setContent(caterogyDTOS);
        caterogyResponse.setPageNumber(caterogyPage.getNumber());
        caterogyResponse.setPageSize(caterogyPage.getSize());
        caterogyResponse.setTotalPages(caterogyPage.getTotalPages());
        caterogyResponse.setTotalElements(caterogyPage.getTotalElements());
        caterogyResponse.setLastPage(caterogyPage.isLast());
        return caterogyResponse;
    }

    @Override
    public CaterogyDTO createCaterogy(CaterogyDTO caterogyDTO) {
        Caterogy caterogy =modelMapper.map(caterogyDTO, Caterogy.class);
        Caterogy CaterogyfromDB=caterogyRepository.findByCaterogyName(caterogyDTO.getCaterogyName());
        if(CaterogyfromDB!=null)
            throw new ApiException("Caterogy with the name"+caterogyDTO.getCaterogyName()+"already exist!!!");
       Caterogy saveCaterogy= caterogyRepository.save(caterogy);
       CaterogyDTO savecaterogyDTO=modelMapper.map(saveCaterogy, CaterogyDTO.class);
       return savecaterogyDTO;
    }

    @Override
    public CaterogyDTO deleteCaterogy(Long caterogyID) {
        Caterogy caterogy = caterogyRepository.findById(caterogyID)
                .orElseThrow(() -> new RescourceNotFoundException("Caterogy", "caterogyID", caterogyID));

        caterogyRepository.delete(caterogy);
        return modelMapper.map(caterogy, CaterogyDTO.class);
    }
    @Override
    public CaterogyDTO updateCaterogy(CaterogyDTO caterogyDTO,Long caterogyID) {
        Caterogy saveCaterogy=caterogyRepository.findById(caterogyID)
                .orElseThrow(()->new RescourceNotFoundException("Caterogy","caterogyID",caterogyID));
        Caterogy caterogy=modelMapper.map(caterogyDTO, Caterogy.class);
        caterogy.setCaterogyID(caterogyID);
        saveCaterogy=caterogyRepository.save(caterogy);
        return modelMapper.map(saveCaterogy, CaterogyDTO.class);
    }

}
