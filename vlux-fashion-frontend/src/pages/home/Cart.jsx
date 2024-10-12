import React, { useEffect, useState } from 'react';
import { Button, Typography, Row, Col, Divider, message, Modal } from 'antd';
import CustomerLayout from '../../layouts/CustomerLayout';
import { useNavigate } from 'react-router-dom'; // Import useNavigate instead

const { Title, Text } = Typography;

const Cart = () => {
    const [cartItems, setCartItems] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    const navigate = useNavigate(); // Use useNavigate instead

    // Fetch cart items from localStorage on component mount
    useEffect(() => {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        setCartItems(cart);
        calculateTotalPrice(cart);
    }, []);

    // Calculate total price of cart items
    const calculateTotalPrice = (cart) => {
        const total = cart.reduce((acc, item) => acc + item.unitPrice * item.quantity, 0);
        setTotalPrice(total);
    };

    const handleCheckout = () => {
        // Navigate to the checkout page
        navigate('/home/checkout'); // Use navigate instead of history.push
    };

    // Handle removing an item from the cart
    const handleRemoveItem = (index) => {
        const itemToRemove = cartItems[index];

        // Show confirmation modal
        Modal.confirm({
            title: `Bạn có chắc chắn muốn xóa "${itemToRemove.productName}" khỏi giỏ hàng?`,
            onOk: () => {
                const updatedCart = cartItems.filter((_, i) => i !== index); // Remove the item at the given index
                setCartItems(updatedCart);
                localStorage.setItem('cart', JSON.stringify(updatedCart)); // Update localStorage
                calculateTotalPrice(updatedCart); // Recalculate total price
                message.success(`${itemToRemove.productName} đã được xóa khỏi giỏ hàng.`);
            },
            onCancel: () => {
                message.info('Hành động đã bị hủy.');
            }
        });
    };

    if (cartItems.length === 0) {
        return (
            <CustomerLayout>
                <div style={{ padding: '20px' }}>
                    <Title level={2}>Giỏ hàng của bạn</Title>
                    <Text>Giỏ hàng hiện đang trống.</Text>
                </div>
            </CustomerLayout>
        );
    }

    return (
        <CustomerLayout>
            <div style={{ padding: '20px' }}>
                <Title level={2}>Giỏ hàng của bạn</Title>

                {cartItems.map((item, index) => (
                    <div key={index} style={{ marginBottom: '20px' }}>
                        <Row gutter={16} align="middle">
                            <Col span={12}>
                                <Text strong>{item.productName}</Text> <br />
                                <Text>Color ID: {item.colorId}</Text> <br />
                                <Text>Size: {item.sizeId}</Text> <br />
                                <Text>Số lượng: {item.quantity}</Text> <br />
                                <Text>Giá: {item.unitPrice.toLocaleString()} VND</Text> <br />
                            </Col>
                            <Col span={12} style={{ textAlign: 'right' }}>
                                <Text strong>
                                    Tổng cộng: {(item.unitPrice * item.quantity).toLocaleString()} VND
                                </Text> <br />
                                <Button
                                    type="danger"
                                    onClick={() => handleRemoveItem(index)}
                                    style={{ marginTop: '10px' }}
                                >
                                    Xóa khỏi giỏ hàng
                                </Button>
                            </Col>
                        </Row>
                        <Divider />
                    </div>
                ))}

                <Row>
                    <Col span={12}>
                        <Title level={4}>Tổng giá trị:</Title>
                    </Col>
                    <Col span={12} style={{ textAlign: 'right' }}>
                        <Title level={4}>{totalPrice.toLocaleString()} VND</Title>
                    </Col>
                </Row>

                <Button type="primary" style={{ marginTop: '20px' }} onClick={handleCheckout}>
                    Thanh toán
                </Button>
            </div>
        </CustomerLayout>
    );
};

export default Cart;
