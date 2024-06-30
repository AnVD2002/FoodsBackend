package com.example.Food.Service.Foods.PropertiesCRUD;

import com.example.Food.DTO.Request.ClientRequest.UpdatePropertiesRequest;
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
public class PropertiesService implements ImplPropertiesService {
    @Autowired
    private PropertiesRepository propertiesRepository;
    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;

    @Override
    public ResponseEntity<?> addProperties(String name){
        if(name.isEmpty()){
            return new ResponseEntity<>("name is empty", HttpStatus.BAD_REQUEST);
        }
        Properties properties = new Properties();
        properties.setPropertyName(name);
        propertiesRepository.save(properties);
        return new ResponseEntity<>("Properties added successfully", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> updateProperties(UpdatePropertiesRequest request){
        if(request.getPropertyID()==null){
            return new ResponseEntity<>("propertyID is empty", HttpStatus.BAD_REQUEST);
        }
        if(request.getPropertyName().isEmpty()){
            return new ResponseEntity<>("propertyName is empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Properties> properties = propertiesRepository.findById(request.getPropertyID());
        if(properties.isEmpty()){
            return new ResponseEntity<>("property not found", HttpStatus.NOT_FOUND);
        }
        properties.get().setPropertyName(request.getPropertyName());
        propertiesRepository.save(properties.get());
        return new ResponseEntity<>("properties updated successfully", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> deleteProperties(Integer propertyID){
        if(propertyID==null){
            return new ResponseEntity<>("propertyID is empty", HttpStatus.BAD_REQUEST);
        }
        Optional<Properties> properties = propertiesRepository.findById(propertyID);
        if(properties.isEmpty()){
            return new ResponseEntity<>("property not found", HttpStatus.NOT_FOUND);
        }
        List<PropertyDetails> propertyDetails = properties.get().getPropertyDetails();
        propertyDetailsRepository.deleteAll(propertyDetails);
        propertiesRepository.delete(properties.get());
        return new ResponseEntity<>("properties deleted successfully", HttpStatus.OK);
    }



}
