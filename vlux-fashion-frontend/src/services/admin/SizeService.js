// SizeService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/product-sizes';

const SizeService = {
  findByProductColorId: (colorId) => axios.get(`${API_URL}/product-color/${colorId}`).then(response => response.data),
  
  createProductSize: (sizeData) => axios.post(API_URL, sizeData).then(response => response.data),
  
  deleteProductSize: (sizeId) => axios.delete(`${API_URL}/${sizeId}`).then(response => response.data),
};

export default SizeService;
