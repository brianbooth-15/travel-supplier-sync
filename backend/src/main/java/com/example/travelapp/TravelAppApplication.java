import graphql.GraphQL;
import graphql.schema.*;
import com.example.travelapp.graphql.UserQuery;
import com.example.travelapp.graphql.CategoryQuery;
import com.example.travelapp.graphql.Mutation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import graphql.schema.GraphQLScalarType;
import graphql.schema.Coercing;
import graphql.language.StringValue;
import graphql.language.IntValue;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = "com.example.travelapp")
public class TravelAppApplication {

    private final UserQuery userQuery;
    private final CategoryQuery categoryQuery;
    private final Mutation mutation;

    // Constructor injection for your GraphQL queries and mutation
    public TravelAppApplication(UserQuery userQuery, CategoryQuery categoryQuery, Mutation mutation) {
        this.userQuery = userQuery;
        this.categoryQuery = categoryQuery;
        this.mutation = mutation;
    }

    // Main method to run the Spring Boot application
    public static void main(String[] args) {
        SpringApplication.run(TravelAppApplication.class, args);
    }

    // Bean configuration for GraphQL schema
    @Bean
    public GraphQL graphQL() {
        // Create custom scalar types for String and Int with coercing
        GraphQLScalarType graphQLString = GraphQLScalarType.newScalar()
                .name("String")
                .coercing(new Coercing<String, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) {
                        return dataFetcherResult == null ? null : dataFetcherResult.toString();
                    }

                    @Override
                    public String parseValue(Object input) {
                        return input == null ? null : input.toString();
                    }

                    @Override
                    public String parseLiteral(Object input) {
                        if (input instanceof StringValue) {
                            return ((StringValue) input).getValue();
                        }
                        return null;
                    }
                })
                .build();

        GraphQLScalarType graphQLInt = GraphQLScalarType.newScalar()
                .name("Int")
                .coercing(new Coercing<Integer, Integer>() {
                    @Override
                    public Integer serialize(Object dataFetcherResult) {
                        return dataFetcherResult == null ? null : (Integer) dataFetcherResult;
                    }

                    @Override
                    public Integer parseValue(Object input) {
                        return input == null ? null : Integer.valueOf(input.toString());
                    }

                    @Override
                    public Integer parseLiteral(Object input) {
                        if (input instanceof IntValue) {
                            return ((IntValue) input).getValue().intValue();
                        }
                        return null;
                    }
                })
                .build();

        // Define GraphQL Object Types for queries
        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query")
                .field(field -> field
                        .name("allUsers")
                        .type(new GraphQLList(GraphQLObjectType.newObject()
                                .name("User")
                                .field(f -> f.name("id").type(graphQLInt))        // Use GraphQLInt for ID field
                                .field(f -> f.name("username").type(graphQLString)) // Use GraphQLString for username
                                .build())) // Define the User object type for list items
                        .dataFetcher(userQuery.getAllUsers())) // Set DataFetcher for all users query
                .field(field -> field
                        .name("getUserById")
                        .type(GraphQLObjectType.newObject()
                                .name("User")
                                .field(f -> f.name("id").type(graphQLInt))       // Use GraphQLInt for ID field
                                .field(f -> f.name("username").type(graphQLString)) // Use GraphQLString for username
                                .build()) // Define the User object type for single user
                        .dataFetcher(userQuery.getUserById())) // Set DataFetcher for user by ID query
                .build();

        // Define the GraphQLObjectType for mutations
        GraphQLObjectType mutationType = GraphQLObjectType.newObject()
                .name("Mutation")
                .field(field -> field
                        .name("createUser")
                        .type(GraphQLObjectType.newObject()
                                .name("User")
                                .field(f -> f.name("id").type(graphQLInt))
                                .field(f -> f.name("username").type(graphQLString))
                                .build())  // Define the User object for mutation result
                        .dataFetcher(mutation.createUser()))  // Set the DataFetcher for the mutation
                .field(field -> field
                        .name("updateUser")
                        .type(GraphQLObjectType.newObject()
                                .name("User")
                                .field(f -> f.name("id").type(graphQLInt))
                                .field(f -> f.name("username").type(graphQLString))
                                .build())  // Define the User object for mutation result
                        .dataFetcher(mutation.updateUser()))  // Set the DataFetcher for the mutation
                .field(field -> field
                        .name("createCategory")
                        .type(GraphQLObjectType.newObject()
                                .name("Category")
                                .field(f -> f.name("id").type(graphQLInt))
                                .field(f -> f.name("name").type(graphQLString))
                                .field(f -> f.name("description").type(graphQLString))
                                .build())  // Define the Category object for mutation result
                        .dataFetcher(mutation.createCategory()))  // Set the DataFetcher for the mutation
                .build();

        // Create the GraphQL schema
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(queryType) // Register query type
                .mutation(mutationType) // Register mutation type
                .build();

        // Return the GraphQL instance using the schema
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
