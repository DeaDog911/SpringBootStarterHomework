package org.deadog.springbootstarterhomework.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @ToString.Exclude
    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Order> orders;

}
