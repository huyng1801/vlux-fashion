// src/services/HomeService.js

import axios from 'axios';

const BASE_URL = 'http://localhost:8080/home'; // Base URL for your API endpoints


export const getAllBanners = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/banners`);
        return response.data; // Assuming the response data is a list of banners
    } catch (error) {
        console.error("Error fetching banners:", error);
        throw error; // Rethrow the error for further handling
    }
};
// Fetch all categories
export const getAllCategories = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/categories`);
        return response.data; // Assuming the response data is a list of categories
    } catch (error) {
        console.error("Error fetching categories:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Fetch subcategories based on categoryId and gender
export const getSubCategories = async (categoryId, gender) => {
    try {
        const response = await axios.get(`${BASE_URL}/subcategories`, {
            params: {
                categoryId,
                gender,
            },
        });
        return response.data; // Assuming the response data is a list of subcategories
    } catch (error) {
        console.error("Error fetching subcategories:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Fetch all products based on various filtering parameters
export const getAllProducts = async (subCategoryId, gender, productName) => {
    try {
        const response = await axios.get(`${BASE_URL}/products`, {
            params: {
                subCategoryId,
                gender,
                productName,
            },
        });
        return response.data; // Assuming the response data is a list of products
    } catch (error) {
        console.error("Error fetching products:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Fetch all sizes based on product color ID
export const getSizesByProductColorId = async (productColorId) => {
    try {
        const response = await axios.get(`${BASE_URL}/product-size/product-color/${productColorId}`);
        return response.data; // Assuming the response data is a list of sizes
    } catch (error) {
        console.error("Error fetching sizes for product color ID:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Fetch all images based on product color ID
export const getImagesByProductColorId = async (productColorId) => {
    try {
        const response = await axios.get(`${BASE_URL}/product-image/product-color/${productColorId}`);
        return response.data; // Assuming the response data is a list of images
    } catch (error) {
        console.error("Error fetching images for product color ID:", error);
        throw error; // Rethrow the error for further handling
    }
};
export const getProductColorsByProductId = async (productId) => {
    try {
        const response = await axios.get(`${BASE_URL}/product-color/${productId}`);
        return response.data; // Assuming the response data is a list of product colors
    } catch (error) {
        console.error("Error fetching product colors for product ID:", error);
        throw error; // Rethrow the error for further handling
    }
};
export const getProductById = async (productId) => {
    try {
        const response = await axios.get(`${BASE_URL}/product/${productId}`);
        return response.data; // Assuming the response data contains the product details
    } catch (error) {
        console.error("Error fetching product by ID:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Create a new order
export const createOrder = async (orderData) => {
    try {


        // Send the request with the payload in the body
        const response = await axios.post(`${BASE_URL}/orders`, orderData);

        return response.data; // Assuming the response contains order details
    } catch (error) {
        console.error("Error creating order:", error);
        throw error; // Rethrow the error for further handling
    }
};

// Login method to authenticate a user
export const login = async (email, password) => {
    try {
        const response = await axios.post(`${BASE_URL}/login`, {
            email,
            password,
        });
        return response.data; 
    } catch (error) {
        console.error("Error logging in:", error);
        throw error; 
    }
};

// Register method to create a new user
export const register = async (registerData) => {
    try {
        const response = await axios.post(`${BASE_URL}/register`, registerData);
        return response.data; 
    } catch (error) {
        console.error("Error registering user:", error);
        throw error; 
    }
};