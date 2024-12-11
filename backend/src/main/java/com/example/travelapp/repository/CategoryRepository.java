package com.example.travelapp.repository;

import com.example.travelapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find a category by its name
    Optional<Category> findByName(String name);
}
