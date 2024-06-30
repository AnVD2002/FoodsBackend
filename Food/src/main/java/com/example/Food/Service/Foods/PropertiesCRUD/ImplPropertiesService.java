package com.example.Food.Service.Foods.PropertiesCRUD;

import com.example.Food.DTO.Request.ClientRequest.UpdatePropertiesRequest;
import org.springframework.http.ResponseEntity;

public interface ImplPropertiesService {
    public ResponseEntity<?> addProperties(String name);
    public ResponseEntity<?> updateProperties(UpdatePropertiesRequest request);
    public ResponseEntity<?> deleteProperties(Integer propertyID);
}
