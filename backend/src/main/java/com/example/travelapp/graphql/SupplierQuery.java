package com.example.travelapp.graphql;

import com.example.travelapp.model.Supplier;
import com.example.travelapp.repository.SupplierRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SupplierQuery {

    private final SupplierRepository supplierRepository;

    public SupplierQuery(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public DataFetcher<List<Supplier>> getAllSuppliers() {
        return dataFetchingEnvironment -> supplierRepository.findAll();
    }
}
