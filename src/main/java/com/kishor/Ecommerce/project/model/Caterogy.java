package com.kishor.Ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caterogy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long caterogyID;
    @NotBlank
    @Size(min = 5,message = "name must be contoins 5 charcaters")
    private String caterogyName;

//    public Caterogy(Long caterogyID,String caterogyName){
//        this.caterogyID=caterogyID;
//        this.caterogyName=caterogyName;
//    }
//
//    public Caterogy() {
//
//    }
//
//    public Long getCaterogyID(){
//        return caterogyID;
//    }
//    public void setCaterogyID(Long caterogyID){
//        this.caterogyID=caterogyID;
//    }
//    public String getCaterogyName(){
//        return caterogyName;
//    }
//    public void setCaterogyName(String caterogyName) {
//        this.caterogyName = caterogyName;
//    }
}
