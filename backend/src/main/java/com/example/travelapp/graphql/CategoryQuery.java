package com.example.travelapp.graphql;

import com.example.travelapp.model.Category;
import com.example.travelapp.repository.CategoryRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryQuery {

    private final CategoryRepository categoryRepository;

    public CategoryQuery(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Fetch all categories
    public DataFetcher<List<Category>> getAllCategories() {
        return dataFetchingEnvironment -> categoryRepository.findAll();
    }

    // Fetch category by ID
    public DataFetcher<Optional<Category>> getCategoryById() {
        return dataFetchingEnvironment -> {
            Long id = dataFetchingEnvironment.getArgument("id");
            return categoryRepository.findById(id);
        };
    }
}
