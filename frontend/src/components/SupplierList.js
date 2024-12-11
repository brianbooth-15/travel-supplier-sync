import React from 'react';
import { useQuery, gql } from '@apollo/client';
import './SupplierList.css';  // Import the CSS file


// GraphQL query to fetch suppliers
const GET_SUPPLIERS = gql`
  query {
    suppliers {
      id
      name
      contactInfo
    }
  }
`;

function SupplierList() {
  // Execute the query
  const { loading, error, data } = useQuery(GET_SUPPLIERS);

  // Loading state
  if (loading) {
    return <p>Loading suppliers...</p>;
  }

  // Error handling
  if (error) {
    console.error("Error fetching suppliers:", error);  // Log error to the console for debugging
    return (
      <div>
        <p>Error: {error.message}</p>
        <button onClick={() => window.location.reload()}>Retry</button> {/* Retry button */}
      </div>
    );
  }

  return (
    <div>
      <h2>Suppliers</h2>
      {/* If no suppliers, show message */}
      {data.suppliers.length === 0 ? (
        <p>No suppliers found.</p>
      ) : (
        <ul>
          {data.suppliers.map((supplier) => (
            <li key={supplier.id}>
              <strong>{supplier.name}</strong>
              <p>{supplier.contactInfo}</p> {/* Display additional info like contact */}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default SupplierList;
