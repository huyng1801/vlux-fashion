import axios from "axios";

const API_URL = "http://localhost:8080/brands"; // Update with your actual API base URL if necessary

// Fetch all brands
export const getAllBrands = async () => {
    try {
        return await axios.get(API_URL); // Return the full Axios response
    } catch (error) {
        console.error("Failed to fetch brands:", error);
        throw error; // Rethrow the error for handling in the calling function
    }
};

// Fetch a brand by its ID
export const getBrandById = async (id) => {
    try {
        return await axios.get(`${API_URL}/${id}`); // Return the full Axios response
    } catch (error) {
        console.error(`Failed to fetch brand with ID ${id}:`, error);
        throw error; // Rethrow the error for handling in the calling function
    }
};

// Create a new brand
export const createBrand = async (brandData) => {
    try {
        const formData = new FormData();
        formData.append("brandName", brandData.brandName);
        if (brandData.imageFile) {
            formData.append("imageFile", brandData.imageFile);
        }
        return await axios.post(API_URL, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }); // Return the full Axios response
    } catch (error) {
        console.error("Failed to create brand:", error);
        throw error; // Rethrow the error for handling in the calling function
    }
};

// Update an existing brand by ID
export const updateBrand = async (id, brandData) => {
    try {
        const formData = new FormData();
        formData.append("brandName", brandData.brandName);
        if (brandData.imageFile) {
            formData.append("imageFile", brandData.imageFile);
        }
        return await axios.put(`${API_URL}/${id}`, formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }); // Return the full Axios response
    } catch (error) {
        console.error(`Failed to update brand with ID ${id}:`, error);
        throw error; // Rethrow the error for handling in the calling function
    }
};

// Delete a brand by ID
export const deleteBrand = async (id) => {
    try {
        return await axios.delete(`${API_URL}/${id}`); // Return the full Axios response
    } catch (error) {
        console.error(`Failed to delete brand with ID ${id}:`, error);
        throw error; // Rethrow the error for handling in the calling function
    }
};

// Default export containing all service methods
// eslint-disable-next-line import/no-anonymous-default-export
export default {
    getAllBrands,
    getBrandById,
    createBrand,
    updateBrand,
    deleteBrand,
};
