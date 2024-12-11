package com.example.travelapp.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // The name of the supplier (e.g., "Expedia", "Booking.com")

    @Column(nullable = false)
    private String apiKey; // The API key used to authenticate with the supplier's API

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // The category the supplier belongs to (e.g., Hotel, Cruise)

    // Default constructor
    public Supplier() {}

    // Constructor with parameters
    public Supplier(String name, String apiKey, Category category) {
        this.name = name;
        this.apiKey = apiKey;
        this.category = category;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Override equals and hashCode for correct comparisons and hash-based collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Optional: Override toString for easy printing of objects
    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", category=" + category.getName() +
                '}';
    }
}
