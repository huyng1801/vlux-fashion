import axios from 'axios';

const API_URL = "http://localhost:8080/categories";  // Change the port if your Spring Boot app runs on a different port.

const getAllCategories = () => {
  return axios.get(API_URL);
};

const getCategoryById = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

const createCategory = (category) => {
  return axios.post(API_URL, category);
};

const updateCategory = (id, category) => {
  return axios.put(`${API_URL}/${id}`, category);
};

const deleteCategory = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
  getAllCategories,
  getCategoryById,
  createCategory,
  updateCategory,
  deleteCategory,
};
