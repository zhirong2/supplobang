import React, { useState, useEffect, useRef } from 'react';
import { FaShoppingCart, FaTimes } from 'react-icons/fa';
import logo from '../../resources/images/supplobang.jpg';
import product from '../../resources/images/product.jpg';
import ProductItem from './ProductItem';
import './Cart.css'; // Import your CSS file for styling



const Cart = () => {
  const [showCart, setShowCart] = useState(false);
  const [cartItems, setCartItems] = useState([
    { id: 1, name: 'Product 1', price: 20, quantity: 2, flavour: 'good', image: logo },
    { id: 2, name: 'Product 2', price: 30, quantity: 1, flavour: 'bad', image: product },
    // Add more items as needed
  ]);

  const handleQuantityChange = (itemId, newQuantity) => {
    const updatedCart = cartItems.map((item) =>
      item.id === itemId ? { ...item, quantity: newQuantity } : item
    );
    setCartItems(updatedCart);
  };

  const calculateTotalPrice = () => {
    return cartItems.reduce((acc, item) => acc + item.quantity * item.price, 0);
  };

  useEffect(() => {
    const totalPrice = calculateTotalPrice();
    console.log('Total Price:', totalPrice);
    // Use totalPrice as needed in your component
  }, [cartItems]);

  const handleCartClick = () => {
    setShowCart(!showCart);
    const body = document.body;
    body.classList.toggle('overlay-active', showCart);
  };

  return (
    <>
      <div className="icon" onClick={handleCartClick}>
        <FaShoppingCart />
        <span className="cart-items-count">{cartItems.length}</span>
      </div>

      {showCart && (
        <>
          <div className="background-overlay" />
          <div className="cart-slider">
            <div className="cart-header">
              <h2 className="cart-title">Cart</h2>
              <button className="close-cart" onClick={() => setShowCart(false)}>
                <FaTimes />
              </button>
            </div>
            <div className="cart-items">
              {cartItems.map((item) => (
                <ProductItem
                  key={item.id}
                  item={item}
                  onQuantityChange={(newQuantity) => handleQuantityChange(item.id, newQuantity)}
                />
              ))}
            </div>
            <div className="cart-total">
              <p className="total-detail">Total: ${calculateTotalPrice()}</p>
              <p className="total-detail">(Inclusive of GST)</p>
              <button className="checkout-button">
                <span>Checkout</span>
              </button>
            </div>
          </div>
        </>
      )}
    </>
  );
};

export default Cart;


