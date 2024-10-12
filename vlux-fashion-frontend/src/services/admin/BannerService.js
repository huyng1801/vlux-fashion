import axios from 'axios';

const API_URL = 'http://localhost:8080/banners'; // Adjust based on your API URL

const getAllBanners = () => {
  return axios.get(API_URL);
};

const getBannerById = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

const createBanner = (bannerData) => {
  const formData = new FormData();
  formData.append('title', bannerData.title);
  formData.append('link', bannerData.link);
  formData.append('isVisible', bannerData.isVisible);
  
  // Check if imageFile exists and append it
  if (bannerData.imageFile) {
    formData.append('imageFile', bannerData.imageFile); // Directly use imageFile if it's a File object
  }
  
  console.log(bannerData);
  return axios.post(API_URL, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

const updateBanner = (id, bannerData) => {
  const formData = new FormData();
  formData.append('title', bannerData.title);
  formData.append('link', bannerData.link);
  formData.append('isVisible', bannerData.isVisible);

  // Check if imageFile exists and append it
  if (bannerData.imageFile) {
    formData.append('imageFile', bannerData.imageFile); // Directly use imageFile if it's a File object
  }

  return axios.put(`${API_URL}/${id}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

const deleteBanner = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

const BannerService = {
  getAllBanners,
  getBannerById,
  createBanner,
  updateBanner,
  deleteBanner,
};

export default BannerService;
