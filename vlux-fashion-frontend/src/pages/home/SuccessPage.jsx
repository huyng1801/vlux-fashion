import React from 'react';
import { Button, Typography } from 'antd';
import { useNavigate } from 'react-router-dom';
import CustomerLayout from '../../layouts/CustomerLayout';

const { Title, Paragraph } = Typography;

const SuccessPage = ({ orderData }) => {
    const navigate = useNavigate(); // Use useNavigate hook for navigation

    // Function to redirect the user to the home page or orders page
    const handleContinueShopping = () => {
        navigate('/home'); // Redirect to the homepage
    };

    const handleViewOrders = () => {
        navigate('/home/orders'); // Redirect to the orders history page
    };

    return (
        <CustomerLayout>
            <div className="success-page" style={{ padding: '20px' }}>
                <Title level={2}>Cảm Ơn Bạn!</Title>
                <Paragraph>
                    Đơn hàng của bạn đã được tạo thành công!
                </Paragraph>
                <Title level={4}>Thông Tin Đơn Hàng</Title>
                {orderData ? (
                    <div>
                        <p><strong>Họ và Tên:</strong> {orderData.guestDto.fullName}</p>
                        <p><strong>Email:</strong> {orderData.guestDto.email}</p>
                        <p><strong>Số Điện Thoại:</strong> {orderData.guestDto.phone}</p>
                        <p><strong>Địa Chỉ:</strong> {orderData.guestDto.address}, {orderData.guestDto.city}</p>
                        <p><strong>Tổng Giá:</strong> {orderData.orderDto.totalPrice.toLocaleString()} VND</p>
                        <p><strong>Ghi Chú:</strong> {orderData.orderDto.orderNote}</p>
                    </div>
                ) : (
                    <Paragraph>Không có thông tin đơn hàng.</Paragraph>
                )}
                <Button type="primary" onClick={handleContinueShopping}>
                    Tiếp Tục Mua Sắm
                </Button>
                <Button type="default" onClick={handleViewOrders} style={{ marginLeft: '10px' }}>
                    Xem Đơn Hàng Của Tôi
                </Button>
            </div>
        </CustomerLayout>
    );
};

export default SuccessPage;
