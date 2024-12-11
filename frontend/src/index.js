// src/index.js
import React from 'react';
import ReactDOM from 'react-dom/client'; // Importing createRoot from 'react-dom/client'
import './index.css'; // Optional: If you want to add global styles
import App from './App'; // Import your main App component
import { ApolloProvider, InMemoryCache, ApolloClient } from '@apollo/client'; // For GraphQL

// Set up Apollo Client
const client = new ApolloClient({
  uri: process.env.REACT_APP_GRAPHQL_URI, // Uses the environment variable
  cache: new InMemoryCache(),
});

// Create a root using createRoot instead of render (for React 18)
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <ApolloProvider client={client}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </ApolloProvider>
);
