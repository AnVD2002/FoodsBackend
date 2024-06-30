package com.example.Food.Service.Foods.PropertyDetailCRUD;

import com.example.Food.DTO.Request.ClientRequest.AddPropertyDetailRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdatePropertyDetailRequest;
import org.springframework.http.ResponseEntity;

public interface ImplPropertyDetailService {
    public ResponseEntity<?> addPropertyDetail(AddPropertyDetailRequest request);
    public ResponseEntity<?> updatePropertyDetail(UpdatePropertyDetailRequest request);
    public ResponseEntity<?> deletePropertyDetail(Integer id);
    public ResponseEntity<?> getPropertyDetailByID(Integer id);
}
