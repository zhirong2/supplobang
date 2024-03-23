// ProductPage.js

import React, { useState } from 'react';
import { FaAngleDown, FaAngleUp } from 'react-icons/fa';
import './ProductPage.css';
import NavBar from '../../components/navbar/Navbar';
import Footer from '../../components/footer/Footer';

const ProductPage = () => {
  const [selectedFlavor, setSelectedFlavor] = useState(null);
  const [showDescription, setShowDescription] = useState(false);
  const [cartItems, setCartItems] = useState([]);

  const addToCart = (product) => {
    setCartItems([...cartItems, product]);
  };

  const handleFlavorSelect = (flavorId, flavorName) => {
    if (selectedFlavor?.id === flavorId) {
      setSelectedFlavor(null);
    } else {
      setSelectedFlavor({ id: flavorId, name: flavorName });
      addToCart(flavorName);
    }
  };

  const product = {
    id: 1,
    name: 'Product Name',
    brand: 'Brand Name',
    price: 99.99,
    imageUrl: 'product-image.jpg',
    description: 'Product Description goes here...',
    flavors: [
      { id: 1, name: 'Flavor 1', quantity: 5 },
      { id: 2, name: 'Flavor 2', quantity: 0 },
      { id: 3, name: 'Flavor 3', quantity: 10 },
    ],
  };

  const toggleDescription = () => {
    setShowDescription(!showDescription);
  };
  const handleAddToCart = () => {
    setCartItems([...cartItems, product]);
  };

  return (
    <>
      <NavBar />
      <div className="product-page">
        <div className="filter-section">
          {/* Your filter components */}
        </div>

        <div className="product-image-section">
          <img src={product.imageUrl} alt={product.name} className="product-image" />
        </div>

        <div className="product-details-section">
          <h2>{product.name}</h2>
          <p>{product.brand}</p>
          <p>${product.price}</p>
          <p> Flavour</p>

          <div className="flavor-buttons">
            {product.flavors.map((flavor) => (
              <button
                key={flavor.id}
                className={`flavor-button ${flavor.quantity === 0 ? 'unavailable' : ''} ${selectedFlavor?.id === flavor.id ? 'selected' : ''}`}
                onClick={() => {
                  if (flavor.quantity > 0) {
                    handleFlavorSelect(flavor.id, flavor.name);
                  }
                }}
              >
                {flavor.name}
              </button>
            ))}
          </div>

          <div className="buttons-section">
          <button onClick={handleAddToCart} className="add-to-cart-button">
            Add to Cart
          </button>

          <div className="description-section">
            <button onClick={toggleDescription} className="toggle-description">
              description {showDescription ? <FaAngleUp /> : <FaAngleDown />}
            </button>
            <div className={`description-content ${showDescription ? 'active' : ''}`}>
              <p>{product.description}</p>
            </div>
          </div>
        </div>
      </div>
      </div>
      <Footer />
    </>
  );
};

export default ProductPage;
