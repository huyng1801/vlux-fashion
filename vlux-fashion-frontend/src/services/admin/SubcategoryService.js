import axios from 'axios';

const API_URL = "http://localhost:8080/subcategories";  // Adjust if necessary

const getAllSubcategories = (categoryId, gender) => {
  let url = API_URL;

  // Initialize query parameters
  const params = new URLSearchParams();

  if (categoryId) {
    params.append('categoryId', categoryId);
  }

  if (gender) {
    params.append('gender', gender);
  }

  // Append query parameters to the URL if they exist
  if (params.toString()) {
    url += `?${params.toString()}`;
  }

  return axios.get(url);
};

const getSubcategoryById = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

const createSubcategory = (subcategory) => {
  return axios.post(API_URL, subcategory);
};

const updateSubcategory = (id, subcategory) => {
  return axios.put(`${API_URL}/${id}`, subcategory);
};

const deleteSubcategory = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

// eslint-disable-next-line import/no-anonymous-default-export
export default {
  getAllSubcategories,
  getSubcategoryById,
  createSubcategory,
  updateSubcategory,
  deleteSubcategory,
};
