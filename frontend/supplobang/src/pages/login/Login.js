import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css'; // Import your CSS file for styling
import NavBar from '../../components/navbar/Navbar';
import Footer from '../../components/footer/Footer';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState('');
  const [passwordError, setPasswordError] = useState('');

  const navigate = useNavigate();

  const handleLogin = () => {
    // Basic email validation
    if (!/\S+@\S+\.\S+/.test(email)) {
      setEmailError('Please enter a valid email');
      return;
    } else {
      setEmailError('');
    }

    // Handle login logic here
    console.log('Login logic goes here');
  };

  const handleForgotPassword = () => {
    // Handle forgot password logic here
    console.log('Forgot password logic goes here');
  };

  const handleCreateAccount = () => {
    navigate('/signup');
  };
  

  return (
    <>
    <NavBar/>
    <div className="centered-login-container">
      <form className="login-form">
      <h2 className="login-header">Login</h2>
        <div className="input-group">
          <label htmlFor="email" className="input-label">Email</label>
          <input
            type="email"
            id="email"
            className={`input-field ${emailError && 'error'}`}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          {emailError && <p className="error-message">{emailError}</p>}
        </div>
        <div className="input-group">
          <label htmlFor="password" className="input-label">Password</label>
          <input
            type="password"
            id="password"
            className={`input-field ${passwordError && 'error'}`}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <span className="forgot-password" onClick={handleForgotPassword}>Forgot Password?</span>
          {passwordError && <p className="error-message">{passwordError}</p>}
        </div>
        <button className="login-button" onClick={handleLogin}>Sign In</button>
      </form>
      <button className="create-account-button" onClick={handleCreateAccount}>Create Account</button>
    </div>
    <Footer/>
    </>
    
  );
};

export default Login;
