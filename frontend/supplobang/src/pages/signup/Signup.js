import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Signup.css';
import NavBar from '../../components/navbar/Navbar';

const Signup = () => {
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        username: '',
        password: '',
        phoneNumber: '',
        email: '',
        streetName: '',
        blockNumber: '',
        unitNumber: '',
        postalCode: '',
    });

    const [formErrors, setFormErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const errors = {};

        if (!formData.username) {
            errors.username = 'Username should not be empty';
        } else if (formData.username.length > 25) {
            errors.username = 'Username should not exceed 25 characters';
        }

        if (!formData.password) {
            errors.password = 'Password should not be empty';
        }

        const phoneRegex = /^[89]\d{7}$/;
        if (!formData.phoneNumber) {
            errors.phoneNumber = 'Phone Number should not be empty';
        } else if (!phoneRegex.test(formData.phoneNumber)) {
            errors.phoneNumber = 'Please provide a valid phone number starting with 8 or 9';
        }

        if (!formData.email) {
            errors.email = 'Email should not be empty';
        } else if (!/^\S+@\S+\.\S+$/.test(formData.email)) {
            errors.email = 'Please enter a valid email address';
        }

        if (!formData.streetName) {
            errors.streetName = 'Street Name should not be empty';
        } else if (formData.streetName.length > 25) {
            errors.streetName = 'Street Name should not exceed 25 characters';
        }

        if (!formData.blockNumber) {
            errors.blockNumber = 'Block Number should not be empty';
        } else if (formData.blockNumber.length > 10) {
            errors.blockNumber = 'Block Number should not exceed 10 characters';
        }

        if (!formData.unitNumber) {
            errors.unitNumber = 'Unit Number should not be empty';
        } else if (formData.unitNumber.length > 10) {
            errors.unitNumber = 'Unit Number should not exceed 10 characters';
        }

        if (!formData.postalCode) {
            errors.postalCode = 'Postal Code should not be empty';
        } else if (formData.postalCode.length !== 6) {
            errors.postalCode = 'Postal Code should be 6 characters long';
        }

        setFormErrors(errors);

        if (Object.keys(errors).length === 0) {
            console.log('Submitting form data:', formData);
            navigate('/success');
        }
    };

    return (
        <>
            <NavBar />
            <div className="centered-signup-container">
                <h2 className="signup-header">Sign Up</h2>
                <form className="signup-form" onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label htmlFor="username" className="input-label">
                            Username
                        </label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            className={`input-field ${formErrors.username && 'error'}`}
                        />
                        {formErrors.username && <p className="error-message">{formErrors.username}</p>}
                    </div>


                    <div className="input-group">
                        <label htmlFor="password" className="input-label">
                            Password
                        </label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            className={`input-field ${formErrors.password && 'error'}`}
                        />
                        {formErrors.password && <p className="error-message">{formErrors.password}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="phoneNumber" className="input-label">
                            Phone Number
                        </label>
                        <input
                            type="text"
                            name="phoneNumber"
                            value={formData.phoneNumber}
                            onChange={handleChange}
                            className={`input-field ${formErrors.phoneNumber && 'error'}`}
                        />
                        {formErrors.phoneNumber && <p className="error-message">{formErrors.phoneNumber}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="email" className="input-label">
                            Email
                        </label>
                        <input
                            type="text"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            className={`input-field ${formErrors.email && 'error'}`}
                        />
                        {formErrors.email && <p className="error-message">{formErrors.email}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="streetName" className="input-label">
                            Street Name
                        </label>
                        <input
                            type="text"
                            name="streetName"
                            value={formData.streetName}
                            onChange={handleChange}
                            className={`input-field ${formErrors.streetName && 'error'}`}
                        />
                        {formErrors.streetName && <p className="error-message">{formErrors.streetName}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="blockNumber" className="input-label">
                            Block Number
                        </label>
                        <input
                            type="text"
                            name="blockNumber"
                            value={formData.blockNumber}
                            onChange={handleChange}
                            className={`input-field ${formErrors.blockNumber && 'error'}`}
                        />
                        {formErrors.blockNumber && <p className="error-message">{formErrors.blockNumber}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="unitNumber" className="input-label">
                            Unit Number
                        </label>
                        <input
                            type="text"
                            name="unitNumber"
                            value={formData.unitNumber}
                            onChange={handleChange}
                            className={`input-field ${formErrors.unitNumber && 'error'}`}
                        />
                        {formErrors.unitNumber && <p className="error-message">{formErrors.unitNumber}</p>}
                    </div>

                    <div className="input-group">
                        <label htmlFor="postalCode" className="input-label">
                            Postal Code
                        </label>
                        <input
                            type="text"
                            name="postalCode"
                            value={formData.postalCode}
                            onChange={handleChange}
                            className={`input-field ${formErrors.postalCode && 'error'}`}
                        />
                        {formErrors.postalCode && <p className="error-message">{formErrors.postalCode}</p>}
                    </div>

                    <button className="signup-button" type="submit">
                        Sign Up
                    </button>
                </form>
            </div>
        </>

    );
};

export default Signup;
