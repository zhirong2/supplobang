import React from 'react';
import './Product.css';

const Product = ({ product }) => {
 return (
    <div className="product-container">
      <img src={product.image} alt="product" className="product-image" />
      <div className="product-details">
        <h3 className="product-name">{product.name}</h3>
        <h4 className="product-price">{product.price}</h4>
        <p className="product-description">{product.description}</p>
        <button className="product-button">Buy Now</button>
      </div>
    </div>
 );
};

export default Product;