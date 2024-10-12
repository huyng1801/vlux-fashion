// src/pages/HomePage.jsx

import React, { useEffect, useState } from 'react';
import { Row, Col, Typography, Spin, Button } from 'antd'; // Import Ant Design components
import CustomerLayout from '../../layouts/CustomerLayout'; // Adjust the path as necessary
import Banner from '../../components/home/Banner';
import { getSubCategories, getAllProducts } from '../../services/home/HomeService'; // Adjust the import path as necessary
import { useNavigate } from 'react-router-dom'; 

const { Title } = Typography;

const HomePage = () => {
    const [subCategories, setSubCategories] = useState([]); // State for subcategories
    const [loading, setLoading] = useState(true); // State for loading status
    const [error, setError] = useState(null); // State for error handling

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                // Fetch all subcategories
                const allSubCategories = await getSubCategories(null, null);
                setSubCategories(allSubCategories);
            } catch (err) {
                setError("Không thể tải dữ liệu."); // Handle error in Vietnamese
                console.error(err);
            } finally {
                setLoading(false); // Loading finished
            }
        };

        fetchData(); // Invoke the fetch function
    }, []);

    if (loading) {
        return <Spin size="large" />; // Show loading spinner
    }

    if (error) {
        return <div>{error}</div>; // Display error message
    }

    return (
        <CustomerLayout>
            <div className="home-page">
                <Title level={2}>Chào Mừng đến với VLux Fashion</Title>
                <Banner />

                {/* Subcategories Section */}
                {subCategories.length > 0 && (
                    <div>
                        <Title level={3}>Danh Mục Phụ</Title>
                        {subCategories.map((subCategory) => (
                            <ProductList key={subCategory.subCategoryId} subCategoryId={subCategory.subCategoryId} subCategoryName={subCategory.subCategoryName} />
                        ))}
                    </div>
                )}
            </div>
        </CustomerLayout>
    );
};

// New component to load and display products for a subcategory
const ProductList = ({ subCategoryId, subCategoryName }) => {
    const [products, setProducts] = useState([]); // State for products
    const [loading, setLoading] = useState(true); // State for loading status
    const [error, setError] = useState(null); // State for error handling
    const navigate = useNavigate();
    useEffect(() => {
        const fetchProducts = async () => {
            setLoading(true);
            try {
                const loadedProducts = await getAllProducts(subCategoryId, null, null);
                setProducts(loadedProducts); // Set the fetched products
            } catch (err) {
                setError("Không thể tải sản phẩm."); // Handle error in Vietnamese
                console.error(err);
            } finally {
                setLoading(false); // Loading finished
            }
        };

        fetchProducts(); // Invoke the fetch function
    }, [subCategoryId]);

    if (loading) {
        return <Spin size="small" />; // Show loading spinner for products
    }

    if (error) {
        return <div>{error}</div>; // Display error message for products
    }

    // Filter products that have valid imageUrl
    const validProducts = products.filter(product => product.imageUrl);

    // If no valid products, do not display this subcategory
    if (validProducts.length === 0) {
        return null; // Do not render this section
    }

    return (
        <div style={{ marginBottom: '30px' }}>
            <Title level={4}>{subCategoryName}</Title>
            <Row gutter={16}>
                {validProducts.map((product) => (
                    <Col span={8} key={product.productId}>
                        <div className="product-card" style={styles.productCard} onClick={() => navigate(`/home/product/${product.productId}`)}>
                            <img
                                src={product.imageUrl}
                                alt={product.productName}
                                style={styles.productImage}
                            />
                            <h4 style={styles.productName}>{product.productName}</h4>
                            <p style={styles.productPrice}>{product.unitPrice.toLocaleString('vi-VN')} VNĐ</p>
                            {/* Only show button if the product is valid */}
                        </div>
                    </Col>
                ))}
            </Row>
        </div>
    );
};

// Inline styles for the product card
const styles = {
    productCard: {
        padding: '15px',
        border: '1px solid #ccc',
        borderRadius: '5px',
        textAlign: 'center',
        backgroundColor: '#fff',
        transition: 'box-shadow 0.2s ease',
        cursor: 'pointer'
    },
    productImage: {
        width: '100%',
        height: 'auto',
        marginBottom: '10px'
    },
    productName: {
        fontSize: '16px',
        fontWeight: 'bold',
        margin: '10px 0'
    },
    productPrice: {
        fontSize: '14px',
        color: '#333',
        marginBottom: '10px'
    },
    addButton: {
        width: '100%'
    }
};

export default HomePage;
