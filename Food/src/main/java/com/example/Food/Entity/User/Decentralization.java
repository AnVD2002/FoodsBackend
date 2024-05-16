package com.example.Food.Entity.User;

import com.example.Food.Enum.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Table(name = "decentralization")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Decentralization {
    @Id
    @Column(name = "decentralization_id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer decentralizationID;
    @Column(name= "decentralization_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum decentralizationName;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "decentralization" )
    private List<User> user;
}
