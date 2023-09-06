package com.agarwal.blog.blogapi.exceptions;

import lombok.*;

@Data
@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
