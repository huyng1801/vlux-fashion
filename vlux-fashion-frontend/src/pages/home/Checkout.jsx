import React, { useEffect, useState } from 'react';
import { createOrder } from '../../services/home/HomeService'; // Adjust the import according to your project structure
import { Button, Typography, Form, Input, message } from 'antd';
import CustomerLayout from '../../layouts/CustomerLayout';
import { useNavigate } from 'react-router-dom';
const { Title } = Typography;

const CheckoutPage = () => {
    const navigate = useNavigate();
    // State for guest information, order details, and order items
    const [guestInfo, setGuestInfo] = useState({
        fullName: '',
        email: '',
        phone: '',
        address: '',
        address2: '',
        city: '',
    });

    const [orderInfo, setOrderInfo] = useState({
        totalPrice: 0,
        isPaid: false,
        orderNote: '',
    });

    const [orderItems, setOrderItems] = useState([]);

    // Fetch order items from localStorage on component mount
    useEffect(() => {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        setOrderItems(cart);

        // Calculate total price from cart items
        const totalPrice = cart.reduce((acc, item) => acc + item.unitPrice * item.quantity, 0);
        setOrderInfo((prev) => ({ ...prev, totalPrice }));
    }, []);

    // Handle guest info changes
    const handleGuestChange = (e) => {
        const { name, value } = e.target;
        setGuestInfo((prevGuestInfo) => ({
            ...prevGuestInfo,
            [name]: value,
        }));
    };

    // Handle order info changes
    const handleOrderChange = (e) => {
        const { name, value } = e.target;
        setOrderInfo((prevOrderInfo) => ({
            ...prevOrderInfo,
            [name]: value,
        }));
    };

    const handleSubmit = async (values) => {
        const orderData = {
            guestDto: guestInfo,
            orderDto: orderInfo,
            orderItemDtos: orderItems.map(item => ({
                productSizeId: item.sizeId, // Adjust according to your product properties
                quantity: item.quantity,
                price: item.unitPrice,
            })),
        };
        try {
            const response = await createOrder(orderData.guestDto, orderData.orderDto, orderData.orderItemDtos);
            console.log('Đặt hàng thành công:', response);
            message.success('Đặt hàng thành công!');
            
            // Clear cart after successful order
            localStorage.removeItem('cart');

            // Navigate to success page, passing the order data as state
            navigate('/success', { state: { orderData } });
        } catch (error) {
            console.error('Lỗi khi tạo đơn hàng:', error);
            message.error('Lỗi khi tạo đơn hàng. Vui lòng thử lại.');
        }
    };
    return (
        <CustomerLayout>
            <div className="checkout-page" style={{ padding: '20px' }}>
                <Title level={2}>Thanh Toán</Title>
                <Form onFinish={handleSubmit}>
                    <Title level={4}>Thông Tin Khách</Title>
                    <Form.Item label="Họ và Tên" name="fullName" rules={[{ required: true }]}>
                        <Input type="text" name="fullName" value={guestInfo.fullName} onChange={handleGuestChange} />
                    </Form.Item>
                    <Form.Item label="Email" name="email" rules={[{ required: true, type: 'email' }]}>
                        <Input type="email" name="email" value={guestInfo.email} onChange={handleGuestChange} />
                    </Form.Item>
                    <Form.Item label="Số Điện Thoại" name="phone" rules={[{ required: true }]}>
                        <Input type="tel" name="phone" value={guestInfo.phone} onChange={handleGuestChange} />
                    </Form.Item>
                    <Form.Item label="Địa Chỉ" name="address" rules={[{ required: true }]}>
                        <Input type="text" name="address" value={guestInfo.address} onChange={handleGuestChange} />
                    </Form.Item>
                    <Form.Item label="Địa Chỉ 2 (tùy chọn)">
                        <Input type="text" name="address2" value={guestInfo.address2} onChange={handleGuestChange} />
                    </Form.Item>
                    <Form.Item label="Thành Phố" name="city" rules={[{ required: true }]}>
                        <Input type="text" name="city" value={guestInfo.city} onChange={handleGuestChange} />
                    </Form.Item>

                    <Title level={4}>Thông Tin Đơn Hàng</Title>
                    <Form.Item label="Tổng Giá">
                        <Input type="number" name="totalPrice" value={orderInfo.totalPrice} readOnly />
                    </Form.Item>
                    <Form.Item label="Ghi Chú Đơn Hàng">
                        <Input type="text" name="orderNote" value={orderInfo.orderNote} onChange={handleOrderChange} />
                    </Form.Item>

                    <Title level={4}>Các Mặt Hàng Đặt Hàng</Title>
                    {orderItems.map((item, index) => (
                        <div key={index}>
                            <h4>Item {index + 1}</h4>
                            <p>Tên Sản Phẩm: {item.productName}</p>
                            <p>ID Màu: {item.colorId}</p>
                            <p>Kích Cỡ: {item.sizeId}</p>
                            <p>Số Lượng: {item.quantity}</p>
                            <p>Giá: {item.unitPrice.toLocaleString()} VND</p>
                        </div>
                    ))}

                    <Button type="primary" htmlType="submit">
                        Gửi Đơn Hàng
                    </Button>
                </Form>
            </div>
        </CustomerLayout>
    );
};

export default CheckoutPage;
