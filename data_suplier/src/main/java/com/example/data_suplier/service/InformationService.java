package com.example.data_suplier.service;

import com.example.data_suplier.model.Information;
import com.example.data_suplier.repository.InformationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformationService {

    private final InformationRepository informationRepository;

    public InformationService (InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    public List<Information> getAll() {
        return informationRepository.findAll();
    }

    public Optional<Information> getById(String id) {
        return informationRepository.findById(id);
    }
}
