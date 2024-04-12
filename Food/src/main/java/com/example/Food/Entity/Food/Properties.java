package com.example.Food.Entity.Food;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "properties")
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private int propertyID;
    @Column(name = "property-name")
    private String propertyName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonManagedReference("property-propertyDetails")
    private List<PropertyDetails> propertyDetails;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    @JsonManagedReference("property-foodProperties")
    private List<FoodProperties> foodProperties;

}
