package com.example.Food.Service.Foods.PropertyDetailCRUD;

import com.example.Food.DTO.Request.ClientRequest.AddPropertyDetailRequest;
import com.example.Food.DTO.Request.ClientRequest.UpdatePropertyDetailRequest;
import com.example.Food.DTO.Response.PropertyDetailResponse;
import com.example.Food.Entity.Food.Properties;
import com.example.Food.Entity.Food.PropertyDetails;
import com.example.Food.Repository.PropertiesRepository;
import com.example.Food.Repository.PropertyDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PropertyDetailServiceImpl implements PropertyDetailService {

    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;
    @Autowired
    private PropertiesRepository propertiesRepository;

    @Override
    public ResponseEntity<?> addPropertyDetail(AddPropertyDetailRequest request) {
        Optional<Properties> property = propertiesRepository.findById(request.getPropertyID());
        if(property.isEmpty()){
            return new ResponseEntity<>("not exist property" , HttpStatus.NOT_FOUND);
        }
        if(request.getPropertyDetailName().isEmpty()){
            return new ResponseEntity<>("name is empty", HttpStatus.BAD_REQUEST);
        }
        PropertyDetails propertyDetails = new PropertyDetails();
        propertyDetails.setPropertyDetailName(request.getPropertyDetailName());
        propertyDetails.setProperty(property.get());
        propertyDetailsRepository.save(propertyDetails);
        return new ResponseEntity<>("Property detail added successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updatePropertyDetail(UpdatePropertyDetailRequest request) {
        if(request.getPropertyDetailID()==null){
            return new ResponseEntity<>("propertyDetailID is null" , HttpStatus.BAD_REQUEST);
        }
        if(request.getPropertyDetailName().isEmpty()){
            return new ResponseEntity<>("name is empty", HttpStatus.BAD_REQUEST);
        }
        Optional<PropertyDetails> propertyDetails = propertyDetailsRepository.findById(request.getPropertyDetailID());
        if(propertyDetails.isEmpty()){
            return new ResponseEntity<>("not exist propertyDetail" , HttpStatus.NOT_FOUND);
        }
        PropertyDetails propertyDetail = propertyDetails.get();
        propertyDetail.setPropertyDetailName(request.getPropertyDetailName());
        propertyDetailsRepository.save(propertyDetail);
        return new ResponseEntity<>("Property detail updated successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deletePropertyDetail(Integer id) {
        if(id==null){
            return new ResponseEntity<>("id is null" , HttpStatus.BAD_REQUEST);
        }
        Optional<PropertyDetails> propertyDetails = propertyDetailsRepository.findById(id);
        if(propertyDetails.isEmpty()){
            return new ResponseEntity<>("not exist propertyDetail" , HttpStatus.NOT_FOUND);
        }
        propertyDetailsRepository.delete(propertyDetails.get());
        return new ResponseEntity<>("Property detail deleted successfully", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getPropertyDetailByID(Integer id) {
        if(id==null){
            return new ResponseEntity<>("id is null" , HttpStatus.BAD_REQUEST);
        }

        Optional<Properties> property = propertiesRepository.findById(id);
        if(property.isEmpty()){
            return new ResponseEntity<>("not exist property" , HttpStatus.NOT_FOUND);
        }
        List<PropertyDetails> propertyDetails = property.get().getPropertyDetails();
        if(propertyDetails.isEmpty()){
            return new ResponseEntity<>("not exist property" , HttpStatus.NOT_FOUND);
        }
        List<PropertyDetailResponse> response = propertyDetails
                .stream().map(propertyDetail -> new PropertyDetailResponse(propertyDetail.getPropertyDetailName(),
                        propertyDetail.getPropertyDetailID())).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
