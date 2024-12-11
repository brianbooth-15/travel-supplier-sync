package com.example.travelapp.repository;

import com.example.travelapp.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    // Find a supplier by its name
    Optional<Supplier> findByName(String name);

    // Find all suppliers by category (Category relationship)
    List<Supplier> findByCategoryId(Long categoryId);

    // You can add more custom queries if needed, for example, searching by API key
    Optional<Supplier> findByApiKey(String apiKey);
}
