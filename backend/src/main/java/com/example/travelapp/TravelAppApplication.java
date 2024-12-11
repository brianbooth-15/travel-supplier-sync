package com.example.travelapp;

import com.example.travelapp.graphql.UserQuery;
import com.example.travelapp.graphql.CategoryQuery;
import com.example.travelapp.graphql.Mutation;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    private final UserQuery userQuery;
    private final CategoryQuery categoryQuery;
    private final Mutation mutation;

    public GraphQLConfig(UserQuery userQuery, CategoryQuery categoryQuery, Mutation mutation) {
        this.userQuery = userQuery;
        this.categoryQuery = categoryQuery;
        this.mutation = mutation;
    }

    @Bean
    public GraphQL graphQL() {
        // Build GraphQL schema
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(userQuery.getAllUsers())         // Register user query resolver
                .query(categoryQuery.getAllCategories()) // Register category query resolver
                .mutation(mutation.createUser())        // Register mutation resolver (Create User)
                .mutation(mutation.createCategory())    // Register another mutation resolver (Create Category)
                .build();

        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
