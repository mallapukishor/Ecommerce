package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Caterogy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaterogyRepository extends JpaRepository<Caterogy,Long> {
    Caterogy findByCaterogyName( String caterogyName);
    //@NotBlank @Size(min = 5,message = "name must be contoins 5 charcaters")
}
