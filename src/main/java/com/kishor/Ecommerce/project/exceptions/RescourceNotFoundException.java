package com.kishor.Ecommerce.project.exceptions;

public class RescourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long filedId;
    public RescourceNotFoundException
            (String resourceName,String field,String fieldName,Long filedId){
        super(String.format("%s not found with %s : %s or id : %d",resourceName,field,fieldName,filedId));
        this.resourceName=resourceName;
        this.field =field;
        this.fieldName=fieldName;
        this.filedId=filedId;
    }
    public RescourceNotFoundException(String resourceName, String field, Long filedId) {
        super(String.format("%s not found with %s : %d",
                resourceName, field, filedId));
        this.resourceName = resourceName;
        this.field = field;
        this.filedId = filedId;
    }
    public RescourceNotFoundException(String cart, String email, String emailId) {
    }


}
