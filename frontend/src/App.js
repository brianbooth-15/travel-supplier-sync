// src/App.js
import React from 'react';
import './App.css'; // Optional: Add custom styles
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import HomePage from './components/HomePage';
import CategoryList from './components/CategoryList';
import SupplierList from './components/SupplierList';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Define routes for the pages */}
          <Route path="/" element={<Login />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/categories" element={<CategoryList />} />
          <Route path="/suppliers" element={<SupplierList />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
