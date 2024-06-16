package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerid;

    @Column(name = "name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String phoneNumber;

}
