import React from "react";
import { Link } from "react-router-dom";
import './HomePage.css'; // Import the CSS file for styling

function HomePage() {
  return (
    <div className="homepage-container">
      <h1>Welcome to the Admin Panel</h1>
      <div className="tiles-container">
        <div className="tile">
          <h2>Types of Travel</h2>
          <p>Go here to maintain the types of supported travel</p>
          <Link to="/categories" className="tile-link">Go to Category List</Link>
        </div>
        <div className="tile">
          <h2>Supplier List</h2>
          <p>Go here to maintain the list of supported suppliers</p>
          <Link to="/suppliers" className="tile-link">Go to Supplier List</Link>
        </div>
      </div>
    </div>
  );
}

export default HomePage;
