import React, { useState } from 'react';
import { FaSearch, FaTimes } from 'react-icons/fa';
import './Searchbar.css'; // Import the CSS file

const Searchbar = () => {
  const [showSearchBar, setShowSearchBar] = useState(false);

  const handleSearchClick = () => {
    setShowSearchBar(true);
    document.body.classList.add('overlay-active'); // Add the blur effect
  };

  const handleCloseSearch = () => {
    setShowSearchBar(false);
    document.body.classList.remove('overlay-active'); // Remove the blur effect
  };

  return (
    <div className="search-bar">
      <button className="search-button" onClick={handleSearchClick}>
        <FaSearch />
      </button>
      <div className={`search-input ${showSearchBar ? 'active' : ''}`}>
        <span className="search-indicator">Search...</span>
        <input type="text" placeholder="Search..." />
        <button className="close-search" onClick={handleCloseSearch}>
          <FaTimes />
        </button>
      </div>
    </div>
  );
};


export default Searchbar;
