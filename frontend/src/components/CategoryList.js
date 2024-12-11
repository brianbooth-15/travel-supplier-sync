import React, { useState } from 'react';
import { useQuery, useMutation, gql } from '@apollo/client';
import { useNavigate } from 'react-router-dom';
import './CategoryList.css';  // Assuming CategoryList.css is in the same folder

// GraphQL queries and mutations
const GET_CATEGORIES = gql`
  query {
    categories {
      id
      name
      description
    }
  }
`;

const CREATE_CATEGORY = gql`
  mutation CreateCategory($name: String!, $description: String!) {
    createCategory(name: $name, description: $description) {
      id
      name
      description
    }
  }
`;

const UPDATE_CATEGORY = gql`
  mutation UpdateCategory($id: ID!, $name: String!, $description: String!) {
    updateCategory(id: $id, name: $name, description: $description) {
      id
      name
      description
    }
  }
`;

const DELETE_CATEGORY = gql`
  mutation DeleteCategory($id: ID!) {
    deleteCategory(id: $id) {
      id
    }
  }
`;

function CategoryList() {
  const [newCategory, setNewCategory] = useState({ name: '', description: '' });
  const [editCategory, setEditCategory] = useState(null); // For editing
  const [error, setError] = useState(null);

  const { loading, error: queryError, data } = useQuery(GET_CATEGORIES);
  const [createCategory] = useMutation(CREATE_CATEGORY, {
    refetchQueries: [{ query: GET_CATEGORIES }], // Refetch categories after creation
    onError: (err) => setError(err.message),
  });
  const [updateCategory] = useMutation(UPDATE_CATEGORY, {
    refetchQueries: [{ query: GET_CATEGORIES }], // Refetch categories after update
    onError: (err) => setError(err.message),
  });
  const [deleteCategory] = useMutation(DELETE_CATEGORY, {
    refetchQueries: [{ query: GET_CATEGORIES }], // Refetch categories after deletion
    onError: (err) => setError(err.message),
  });

  const navigate = useNavigate(); // Hook to navigate to different routes

  if (loading) return <p>Loading categories...</p>;
  if (queryError) return <p>Error: {queryError.message}</p>;

  const handleCreateCategory = (e) => {
    e.preventDefault();
    createCategory({ variables: { ...newCategory } });
    setNewCategory({ name: '', description: '' }); // Reset form
  };

  const handleEditCategory = (category) => {
    setEditCategory(category);
    setNewCategory({ name: category.name, description: category.description });
  };

  const handleUpdateCategory = (e) => {
    e.preventDefault();
    updateCategory({ variables: { id: editCategory.id, ...newCategory } });
    setEditCategory(null); // Reset edit mode
    setNewCategory({ name: '', description: '' }); // Reset form
  };

  const handleDeleteCategory = (id) => {
    if (window.confirm('Are you sure you want to delete this category?')) {
      deleteCategory({ variables: { id } });
    }
  };

  return (
    <div className="category-list-container">
      <h2>Categories</h2>

      {/* Category creation form */}
      <form onSubmit={editCategory ? handleUpdateCategory : handleCreateCategory}>
        <div>
          <label htmlFor="category-name">{editCategory ? 'Edit Category Name' : 'Category Name'}</label>
          <input
            id="category-name"
            type="text"
            value={newCategory.name}
            onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
            required
          />
        </div>
        <div>
          <label htmlFor="category-description">{editCategory ? 'Edit Category Description' : 'Category Description'}</label>
          <input
            id="category-description"
            type="text"
            value={newCategory.description}
            onChange={(e) => setNewCategory({ ...newCategory, description: e.target.value })}
            required
          />
        </div>
        <button type="submit">{editCategory ? 'Update Category' : 'Create Category'}</button>
      </form>

      {/* Display any error messages */}
      {error && <p className="error-message">{error}</p>}

      {/* Category list */}
      {data.categories.length === 0 ? (
        <p>No categories found.</p>
      ) : (
        <ul className="category-list">
          {data.categories.map((category) => (
            <li key={category.id} className="category-item">
              <h3>{category.name}</h3>
              <p>{category.description}</p>
              <button onClick={() => handleEditCategory(category)}>Edit</button>
              <button onClick={() => handleDeleteCategory(category.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}
      <div>
        <button onClick={() => navigate('/home')}>Back to Home</button>
      </div>
    </div>
  );
}

export default CategoryList;
