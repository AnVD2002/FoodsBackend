package com.example.Food.Entity.Cart;

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
@Table(name = "promote")
public class Promote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promote_id")
    private Integer promoteID;
    @Column(name = "promote_code")
    private String promoteCode;
    @Column(name = "percent")
    private Double percent;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "promote")
    @JsonManagedReference("promote-carts")
    private List<Carts> carts;
}
