import axios from 'axios';

const API_URL = "http://localhost:8080/products"; // Adjust if necessary

const getAllProducts = (categoryId) => {
  let url = API_URL;

  // Initialize query parameters
  const params = new URLSearchParams();

  if (categoryId) {
    params.append('categoryId', categoryId);
  }

  // Append query parameters to the URL if they exist
  if (params.toString()) {
    url += `?${params.toString()}`;
  }

  return axios.get(url);
};

const getProductById = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

const createProduct = (product) => {
    console.log(product);
  return axios.post(API_URL, product);
};

const updateProduct = (id, product) => {
  return axios.put(`${API_URL}/${id}`, product);
};

const deleteProduct = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
  getAllProducts,
  getProductById,
  createProduct,
  updateProduct,
  deleteProduct,
};
