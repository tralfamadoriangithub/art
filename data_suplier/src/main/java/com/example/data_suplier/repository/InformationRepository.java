package com.example.data_suplier.repository;

import com.example.data_suplier.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, String> {

}
