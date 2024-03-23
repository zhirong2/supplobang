import React from 'react';
import NavBar from '../../components/navbar/Navbar';
import Footer from '../../components/footer/Footer';


const Home = () => {
    return (
        <>
            <NavBar />
            <div className="home">
                <h1>Welcome to Supplobang!</h1>
                <p>
                   supplobang is good
                </p>
            </div>

            <Footer />
        </>

    );
}; 

export default Home;