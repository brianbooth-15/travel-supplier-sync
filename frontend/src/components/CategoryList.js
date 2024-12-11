import React from 'react';
import { useQuery, gql } from '@apollo/client';
import './CategoryList.css';  // Import the CSS for this component

// GraphQL query to fetch categories
const GET_CATEGORIES = gql`
  query {
    categories {
      id
      name
      description
    }
  }
`;

function CategoryList() {
  // Execute the GraphQL query using the `useQuery` hook
  const { loading, error, data } = useQuery(GET_CATEGORIES);

  // Handle loading state
  if (loading) return <p>Loading categories...</p>;

  // Handle error state
  if (error) return <p>Error: {error.message}</p>;

  // Render the category list
  return (
    <div className="category-list-container">
      <h2>Categories</h2>
      {/* If no categories are available */}
      {data.categories.length === 0 ? (
        <p>No categories found.</p>
      ) : (
        <ul className="category-list">
          {data.categories.map((category) => (
            <li key={category.id} className="category-item">
              <h3>{category.name}</h3>
              <p>{category.description}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default CategoryList;
