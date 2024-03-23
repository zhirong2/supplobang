
import { Link } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import logo from '../../resources/images/supplobang.jpg';
import Searchbar from '../searchbar/Searchbar';
import './Navbar.css'; // Import the CSS file
import Cart from '../cart/Cart';

const NavBar = ({productItem}) => {

  return (
    <>
      <nav className="navbar">
        <div className="container">
        <Searchbar/>
          {/* Middle: Logo */}
          <Link to="/" className="navbar-brand">
            <img src={logo} alt="Logo" />
          </Link>
          {/* Right: User Icon and Cart Icon */}
          <div className="icons">
            <Link to="/login" className="icon">
              <FaUser />
            </Link>
                <Cart productItem={productItem}/>
          </div>
        </div>
      </nav>
      {/* Menu items */}
      <nav className="menu-navbar">
        <ul className="menu">
          <li>
            <div className="centered-menu-items">
              <Link to="/featured" className="menu-item">
                FEATURED
              </Link>
              <Link to="/products" className="menu-item">
                PRODUCTS
              </Link>
              <Link to="/category" className="menu-item">
                CATEGORIES
              </Link>
              <Link to="/brand" className="menu-item">
                BRANDS
              </Link>
              <Link to="/about-us" className="menu-item">
                ABOUT US
              </Link>
            </div>
          </li>
        </ul>
      </nav>
    </>
  );
};

export default NavBar;
