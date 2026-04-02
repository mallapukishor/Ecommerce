package com.kishor.Ecommerce.project.payload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaterogyDTO {
    private Long caterogyID;
    private String caterogyName;
    @OneToMany(mappedBy = "caterogy",cascade = CascadeType.ALL)
    private List<ProductDTO>productDTO;
}
