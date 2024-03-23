import React, { useState } from 'react';
import { FaPlus, FaMinus } from 'react-icons/fa';
import './ProductItem.css';

const ProductItem = ({ item }) => {
  const [quantity, setQuantity] = useState(item.quantity);
  const [flavor, setFlavor] = useState(item.flavor);

  const increaseQuantity = () => {
    setQuantity(quantity + 1);
  };

  const decreaseQuantity = () => {
    if (quantity > 0) {
      setQuantity(quantity - 1);
    }
  };

  const handleFlavorChange = (event) => {
    setFlavor(event.target.value);
  };

  return (
    <div className="cart-item">
      <img src={item.image} alt={item.name} className="cart-item-image" />
      <div className="cart-item-details">
        <p className="cart-item-name">{item.name}</p>
        <select value={flavor} onChange={handleFlavorChange} className="cart-item-flavor">
          {/* Dropdown options for flavors */}
          {/* Replace options with your actual flavors */}
          <option value="flavor1">Flavor 1</option>
          <option value="flavor2">Flavor 2</option>
        </select>
        <div className="quantity-control">
          <FaMinus className="quantity-icon" onClick={decreaseQuantity} />
          <span className="quantity-value">{quantity}</span>
          <FaPlus className="quantity-icon" onClick={increaseQuantity} />
        </div>
        <p className="cart-item-price">${item.price}</p>
      </div>
    </div>
  );
};

export default ProductItem;
