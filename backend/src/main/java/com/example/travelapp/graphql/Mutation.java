package com.example.travelapp.graphql;

import com.example.travelapp.model.User;
import com.example.travelapp.model.Category;
import com.example.travelapp.service.UserService;
import com.example.travelapp.service.CategoryService;
import graphql.schema.DataFetcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Mutation {

    private final UserService userService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Mutation(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Mutation to create a new user
    public DataFetcher<User> createUser() {
        return dataFetchingEnvironment -> {
            String username = dataFetchingEnvironment.getArgument("username");
            String email = dataFetchingEnvironment.getArgument("email");
            String password = dataFetchingEnvironment.getArgument("password");

            // Hash the password before saving
            String hashedPassword = passwordEncoder.encode(password);

            User newUser = new User(username, email, hashedPassword);
            return userService.saveUser(newUser);
        };
    }

    // Mutation to update an existing user
    public DataFetcher<User> updateUser() {
        return dataFetchingEnvironment -> {
            Long id = dataFetchingEnvironment.getArgument("id");
            String username = dataFetchingEnvironment.getArgument("username");
            String email = dataFetchingEnvironment.getArgument("email");
            String password = dataFetchingEnvironment.getArgument("password");

            // Fetch the existing user using getUserById
            User existingUser = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            existingUser.setUsername(username);
            existingUser.setEmail(email);

            // If a password is provided, hash it before saving
            if (password != null) {
                String hashedPassword = passwordEncoder.encode(password);
                existingUser.setPassword(hashedPassword);
            }

            return userService.saveUser(existingUser);
        };
    }

    // Mutation to create a new category
    public DataFetcher<Category> createCategory() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            String description = dataFetchingEnvironment.getArgument("description");

            Category newCategory = new Category(name, description);
            return categoryService.saveCategory(newCategory);
        };
    }

    // Mutation to update an existing category
    public DataFetcher<Category> updateCategory() {
        return dataFetchingEnvironment -> {
            Long id = dataFetchingEnvironment.getArgument("id");
            String name = dataFetchingEnvironment.getArgument("name");
            String description = dataFetchingEnvironment.getArgument("description");

            Category existingCategory = categoryService.getCategoryById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            existingCategory.setName(name);
            existingCategory.setDescription(description);

            return categoryService.saveCategory(existingCategory);
        };
    }
}
