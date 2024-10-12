// src/services/ProductColorImageService.js
import axios from 'axios';

const API_URL = 'http://127.0.0.1:8080/product-color-images'; // Replace with your actual API endpoint

const ProductColorImageService = {
  addImageToColor: (formData) => {
    console.log("add" + formData);
    return axios.post(`${API_URL}`, formData).then(response => response.data);
  },

  deleteImageFromColor: (imageId) => {
    return axios.delete(`${API_URL}/${imageId}`).then(response => response.data);
  },

  getImagesByColorId: (colorId) => {
    return axios.get(`${API_URL}/color/${colorId}`).then(response => response.data);
  },
};

export default ProductColorImageService;
