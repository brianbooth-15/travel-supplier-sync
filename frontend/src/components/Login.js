import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom'; // Use Link for better navigation
import './Login.css'; // Assuming Login.css is in the same folder

// Hardcoded users
const USERS = [
  {
    email: 'admin@test.com',
    password: 'test',
    role: 'admin', // Admin user
  },
  {
    email: 'user@test.com',
    password: 'test',
    role: 'user', // General user
  },
];

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false); // Add a loading state

  const navigate = useNavigate(); // Hook to navigate to different routes

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Please fill in both fields');
      return;
    }

    setError(null); // Reset error state
    setLoading(true); // Set loading state

    // Check if the email and password match any hardcoded users
    const user = USERS.find((user) => user.email === email && user.password === password);

    if (user) {
      // If a match is found, store the user token and redirect to home page
      localStorage.setItem('token', 'your-token-here'); // Here you can store an actual token if needed
      localStorage.setItem('role', user.role); // Store the user's role if needed

      // Redirect to the homepage after successful login
      navigate('/home');
    } else {
      // If no match is found, show an error
      setError('Invalid email or password');
    }

    setLoading(false); // Reset loading state
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="Enter your email"
            required
            disabled={loading}
          />
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Enter your password"
            required
            disabled={loading}
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>
      <div>
        <p>
          Don't have an account? <Link to="/signup">Sign Up</Link>
        </p>
      </div>
    </div>
  );
}

export default Login;
