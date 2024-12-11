package com.example.travelapp.graphql;

import com.example.travelapp.model.User;
import com.example.travelapp.repository.UserRepository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserQuery {

    private final UserRepository userRepository;

    public UserQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Fetch all users
    public DataFetcher<List<User>> getAllUsers() {
        return dataFetchingEnvironment -> userRepository.findAll();
    }

    // Fetch user by ID
    public DataFetcher<Optional<User>> getUserById() {
        return dataFetchingEnvironment -> {
            Long id = dataFetchingEnvironment.getArgument("id");
            return userRepository.findById(id);
        };
    }
}