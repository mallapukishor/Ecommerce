package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Caterogy;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaterogyReposistory extends JpaRepository<Caterogy,Long> {
    Caterogy findByCaterogyName( String caterogyName);
    //@NotBlank @Size(min = 5,message = "name must be contoins 5 charcaters")
}
