import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import CategoryPage from './pages/admin/CategoryPage'; // Import the CategoryPage component
import BannerPage from './pages/admin/BannerPage';
import DashboardPage from './pages/admin/DashboardPage';
import BrandPage from './pages/admin/BrandPage';
import SubCategoryPage from './pages/admin/SubCategoryPage';
import ProductPage from './pages/admin/ProductPage';
import OrderPage from './pages/admin/OrderPage';
import HomePage from './pages/home/HomePage';
import ProductDetails from './pages/home/ProductDetails';
import Cart from './pages/home/Cart';
import CheckoutPage from './pages/home/Checkout';
import SuccessPage from './pages/home/SuccessPage';

function App() {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<DashboardPage />} /> 
          <Route path="/categories" element={<CategoryPage />} /> 
          <Route path="/banners" element={<BannerPage />} /> 
          <Route path="/brands" element={<BrandPage />} /> 
          <Route path="/subcategories" element={<SubCategoryPage />} /> 
          <Route path="/products" element={<ProductPage />} /> 
          <Route path="/orders" element={<OrderPage />} /> 
          <Route path="/home" element={<HomePage />} /> 
          <Route path="/home/product/:productId" element={<ProductDetails />} />
          <Route path="/home/cart/" element={<Cart />} />
          <Route path="/home/checkout/" element={<CheckoutPage />} />
          <Route path="/home/success/" element={<SuccessPage />} />
        </Routes>
    </Router>
  );
}

export default App;
