import React, { useState } from 'react';
import { useQuery, useMutation, gql } from '@apollo/client';
import { useNavigate } from 'react-router-dom';
import './SupplierList.css';  // Import the CSS file for styling

// GraphQL queries and mutations
const GET_SUPPLIERS = gql`
  query {
    suppliers {
      id
      name
      contactInfo
    }
  }
`;

const CREATE_SUPPLIER = gql`
  mutation CreateSupplier($name: String!, $contactInfo: String!) {
    createSupplier(name: $name, contactInfo: $contactInfo) {
      id
      name
      contactInfo
    }
  }
`;

const UPDATE_SUPPLIER = gql`
  mutation UpdateSupplier($id: ID!, $name: String!, $contactInfo: String!) {
    updateSupplier(id: $id, name: $name, contactInfo: $contactInfo) {
      id
      name
      contactInfo
    }
  }
`;

const DELETE_SUPPLIER = gql`
  mutation DeleteSupplier($id: ID!) {
    deleteSupplier(id: $id) {
      id
    }
  }
`;

function SupplierList() {
  const [newSupplier, setNewSupplier] = useState({ name: '', contactInfo: '' });
  const [editSupplier, setEditSupplier] = useState(null); // For editing
  const [error, setError] = useState(null);

  const { loading, error: queryError, data } = useQuery(GET_SUPPLIERS);
  const [createSupplier] = useMutation(CREATE_SUPPLIER, {
    refetchQueries: [{ query: GET_SUPPLIERS }],
    onError: (err) => setError(err.message),
  });
  const [updateSupplier] = useMutation(UPDATE_SUPPLIER, {
    refetchQueries: [{ query: GET_SUPPLIERS }],
    onError: (err) => setError(err.message),
  });
  const [deleteSupplier] = useMutation(DELETE_SUPPLIER, {
    refetchQueries: [{ query: GET_SUPPLIERS }],
    onError: (err) => setError(err.message),
  });

  const navigate = useNavigate();

  if (loading) return <p>Loading suppliers...</p>;
  if (queryError) return <p>Error: {queryError.message}</p>;

  const handleCreateSupplier = (e) => {
    e.preventDefault();
    createSupplier({ variables: { ...newSupplier } });
    setNewSupplier({ name: '', contactInfo: '' }); // Reset form
  };

  const handleEditSupplier = (supplier) => {
    setEditSupplier(supplier);
    setNewSupplier({ name: supplier.name, contactInfo: supplier.contactInfo });
  };

  const handleUpdateSupplier = (e) => {
    e.preventDefault();
    updateSupplier({ variables: { id: editSupplier.id, ...newSupplier } });
    setEditSupplier(null); // Reset edit mode
    setNewSupplier({ name: '', contactInfo: '' }); // Reset form
  };

  const handleDeleteSupplier = (id) => {
    if (window.confirm('Are you sure you want to delete this supplier?')) {
      deleteSupplier({ variables: { id } });
    }
  };

  return (
    <div className="supplier-list-container">
      <h2>Suppliers</h2>

      {/* Supplier creation or editing form */}
      <form onSubmit={editSupplier ? handleUpdateSupplier : handleCreateSupplier}>
        <div>
          <label htmlFor="supplier-name">{editSupplier ? 'Edit Supplier Name' : 'Supplier Name'}</label>
          <input
            id="supplier-name"
            type="text"
            value={newSupplier.name}
            onChange={(e) => setNewSupplier({ ...newSupplier, name: e.target.value })}
            required
          />
        </div>
        <div>
          <label htmlFor="supplier-contact">{editSupplier ? 'Edit Supplier Contact Info' : 'Supplier Contact Info'}</label>
          <input
            id="supplier-contact"
            type="text"
            value={newSupplier.contactInfo}
            onChange={(e) => setNewSupplier({ ...newSupplier, contactInfo: e.target.value })}
            required
          />
        </div>
        <button type="submit">{editSupplier ? 'Update Supplier' : 'Create Supplier'}</button>
      </form>

      {/* Display any error messages */}
      {error && <p className="error-message">{error}</p>}

      {/* Supplier list */}
      {data.suppliers.length === 0 ? (
        <p>No suppliers found.</p>
      ) : (
        <ul>
          {data.suppliers.map((supplier) => (
            <li key={supplier.id}>
              <strong>{supplier.name}</strong>
              <p>{supplier.contactInfo}</p>
              <button onClick={() => handleEditSupplier(supplier)}>Edit</button>
              <button onClick={() => handleDeleteSupplier(supplier.id)}>Delete</button>
            </li>
          ))}
        </ul>
      )}

      {/* Button to navigate back to home */}
      <div>
        <button onClick={() => navigate('/home')}>Back to Home</button>
      </div>
    </div>
  );
}

export default SupplierList;
