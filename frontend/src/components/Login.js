import React, { useState } from 'react';
import { useMutation, gql } from '@apollo/client';
import { useNavigate } from 'react-router-dom'; // For redirecting to the dashboard or home page
import './Login.css';  // Assuming Login.css is in the same folder


// GraphQL mutation to log in a user
const LOGIN_USER = gql`
  mutation LoginUser($email: String!, $password: String!) {
    loginUser(email: $email, password: $password) {
      token  # Assume that the mutation returns a token
    }
  }
`;

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);

  const [loginUser] = useMutation(LOGIN_USER, {
    onCompleted: (data) => {
      // Store the token in localStorage or sessionStorage
      localStorage.setItem('token', data.loginUser.token);
      // Redirect to the dashboard or home page
      navigate('/dashboard');
    },
    onError: (err) => {
      // Handle GraphQL errors
      setError(err.message);
    }
  });

  const navigate = useNavigate(); // Hook to navigate to different routes

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Please fill in both fields');
      return;
    }

    // Reset any previous error before submitting
    setError(null);

    // Call the login mutation
    loginUser({ variables: { email, password } });
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
          />
        </div>
        {error && <p className="error-message">{error}</p>}
        <button type="submit">Login</button>
      </form>
      <div>
        <p>Don't have an account? <a href="/signup">Sign Up</a></p>
      </div>
    </div>
  );
}

export default Login;
