package com.example.travelapp.service;

import com.example.travelapp.model.Supplier;
import com.example.travelapp.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // Create or update a supplier
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    // Get a supplier by ID
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    // Get suppliers by category ID
    public List<Supplier> getSuppliersByCategory(Long categoryId) {
        return supplierRepository.findByCategoryId(categoryId);
    }

    // Get a supplier by its API key
    public Optional<Supplier> getSupplierByApiKey(String apiKey) {
        return supplierRepository.findByApiKey(apiKey);
    }

    // Delete a supplier by ID
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
