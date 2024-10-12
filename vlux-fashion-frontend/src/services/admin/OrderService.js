// src/services/admin/OrderService.js
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/orders'; // Replace with your actual API URL

const OrderService = {
  // Fetch all orders
  async getAllOrders() {
    try {
      const response = await axios.get(BASE_URL);
      return response.data; // Adjust to return the actual data
    } catch (error) {
      console.error('Error fetching orders:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },

  // Create a new order
  async createOrder(orderData) {
    try {
      const response = await axios.post(BASE_URL, orderData);
      return response.data; // Adjust to return the actual data
    } catch (error) {
      console.error('Error creating order:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },

  // Update an existing order
  async updateOrder(orderId, orderData) {
    try {
      const response = await axios.put(`${BASE_URL}/${orderId}`, orderData);
      return response.data; // Adjust to return the actual data
    } catch (error) {
      console.error('Error updating order:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },

  // Delete an order
  async deleteOrder(orderId) {
    try {
      const response = await axios.delete(`${BASE_URL}/${orderId}`);
      return response.data; // Adjust to return the actual data
    } catch (error) {
      console.error('Error deleting order:', error);
      throw error; // Rethrow error to handle it in the calling component
    }
  },
};

export default OrderService;
