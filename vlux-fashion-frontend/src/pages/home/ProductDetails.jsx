import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
    getProductById,
    getProductColorsByProductId,
    getSizesByProductColorId,
    getImagesByProductColorId,
} from '../../services/home/HomeService';
import { Spin, Typography, Button, Row, Col, Carousel, message } from 'antd';
import CustomerLayout from '../../layouts/CustomerLayout';

const { Title } = Typography;

const ProductDetails = () => {
    const { productId } = useParams();
    const [productDetails, setProductDetails] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [colors, setColors] = useState([]);
    const [selectedColorId, setSelectedColorId] = useState(null);
    const [sizes, setSizes] = useState([]);
    const [images, setImages] = useState([]);
    const [selectedSizeId, setSelectedSizeId] = useState(null);

    useEffect(() => {
        const fetchProductDetails = async () => {
            setLoading(true);
            try {
                const product = await getProductById(productId);
                setProductDetails(product);

                const productColors = await getProductColorsByProductId(productId);
                setColors(productColors);

                if (productColors && productColors.length > 0) {
                    setSelectedColorId(productColors[0].productColorId); // Set default color
                }
            } catch (err) {
                setError("Không thể tải thông tin sản phẩm.");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchProductDetails();
    }, [productId]);

    useEffect(() => {
        const fetchColorDetails = async () => {
            if (selectedColorId) {
                try {
                    const fetchedSizes = await getSizesByProductColorId(selectedColorId);
                    const fetchedImages = await getImagesByProductColorId(selectedColorId);

                    setSizes(fetchedSizes);
                    setImages(fetchedImages);
                } catch (err) {
                    console.error(err);
                }
            }
        };

        fetchColorDetails();
    }, [selectedColorId]);

    const handleAddToCart = () => {
        if (selectedSizeId) {
            const cartItem = {
                productId,
                productName: productDetails.productName,
                colorId: selectedColorId,
                sizeId: selectedSizeId,
                quantity: 1, // Default to 1 item
                unitPrice: productDetails.unitPrice,
            };

            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            cart.push(cartItem);
            localStorage.setItem('cart', JSON.stringify(cart));
            
            // Use Ant Design's message to show success notification
            message.success('Đã thêm sản phẩm vào giỏ hàng!');
        } else {
            // Use Ant Design's message to show error notification
            message.error('Vui lòng chọn kích cỡ.');
        }
    };

    if (loading) {
        return <Spin size="large" />;
    }

    if (error) {
        return <div>{error}</div>;
    }

    if (!productDetails) {
        return <div>Không có thông tin sản phẩm.</div>;
    }

    return (
        <CustomerLayout>
            <div style={{ padding: '20px' }}>
                <Title level={2}>{productDetails.productName}</Title>
                <Row gutter={16}>
                    <Col span={12}>
                        {/* Image Slider */}
                        <Carousel autoplay>
                            {images.map(image => (
                                <div key={image.productColorImageId}>
                                    <img src={image.imageUrl} alt={productDetails.productName} style={{ width: '100%', height: 'auto' }} />
                                </div>
                            ))}
                        </Carousel>
                    </Col>
                    <Col span={12}>
                        <Title level={4}>Chọn Màu Sắc:</Title>
                        {colors.map(color => (
                            <Button
                                key={color.productColorId}
                                onClick={() => setSelectedColorId(color.productColorId)}
                                style={{ marginRight: '10px', backgroundColor: color.hexCode }}
                            >
                                {color.colorName}
                            </Button>
                        ))}
                        <Title level={4}>Kích Cỡ:</Title>
                        {sizes.map(size => (
                            <Button
                                key={size.productSizeId}
                                onClick={() => setSelectedSizeId(size.productSizeId)}
                                disabled={size.stockQuantity === 0}
                                style={{ marginRight: '10px', marginBottom: '10px' }}
                            >
                                {size.sizeValue}
                            </Button>
                        ))}
                        <Button type="primary" onClick={handleAddToCart}>
                            Thêm vào giỏ hàng
                        </Button>
                    </Col>
                </Row>
            </div>
        </CustomerLayout>
    );
};

export default ProductDetails;
