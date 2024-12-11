@Component
public class Mutation {

    private final UserService userService;
    private final CategoryService categoryService;

    public Mutation(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    // Mutation to create a new user
    public DataFetcher<User> createUser() {
        return dataFetchingEnvironment -> {
            String username = dataFetchingEnvironment.getArgument("username");
            String email = dataFetchingEnvironment.getArgument("email");
            String password = dataFetchingEnvironment.getArgument("password");

            // You would also hash the password here before saving
            User newUser = new User(username, email, password);
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

            User existingUser = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
            existingUser.setUsername(username);
            existingUser.setEmail(email);
            if (password != null) {
                existingUser.setPassword(password); // Consider hashing the password before saving
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
