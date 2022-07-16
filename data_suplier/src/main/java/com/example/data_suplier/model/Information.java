package com.example.data_suplier.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "information")
@Data
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}
