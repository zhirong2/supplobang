// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom';

import './App.css';
import Home from './pages/home/Home';
import Checkout from './pages/checkout/Checkout';
import Login from './pages/login/Login';
import Signup from './pages/signup/Signup'
import ProductPage from './pages/product/ProductPage';


function App() {
 return (
    <Router>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/checkout" element={<Checkout/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/signup" element={<Signup/>} />
        <Route path="/products" element={<ProductPage/>} />
      </Routes>
    </Router>
 );
}

export default App;