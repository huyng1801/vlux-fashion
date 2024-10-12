import axios from 'axios';

const BASE_URL = 'http://localhost:8080/product-colors'; // Replace with your actual API URL

const ColorService = {
  // Fetch colors for a specific product by ID
  async getColorsByProductId(productId) {
    try {
      const response = await axios.get(`${BASE_URL}/product/${productId}`);
      return response.data; // Adjusted to return the actual data
    } catch (error) {
      console.error('Error fetching colors:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },

  // Add a color to a product
  async addColorToProduct(formData) {
    try {
      const response = await axios.post(`${BASE_URL}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data; // Adjusted to return the actual data
    } catch (error) {
      console.error('Error adding color:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },

  // Delete a color from a product
  async deleteColorFromProduct(colorId) {
    try {
      const response = await axios.delete(`${BASE_URL}/${colorId}`);
      return response.data; // Adjusted to return the actual data
    } catch (error) {
      console.error('Error deleting color:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },
};

export default ColorService;
