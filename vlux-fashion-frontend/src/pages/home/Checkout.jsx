import React, { useEffect, useState } from 'react';
import { 
    Form, 
    Input, 
    Button, 
    Typography, 
    Row, 
    Col, 
    Card, 
    Divider, 
    message, 
    Steps,
    Spin
} from 'antd';
import { 
    UserOutlined, 
    ShoppingOutlined, 
    CreditCardOutlined,
    CheckCircleOutlined
} from '@ant-design/icons';
import CustomerLayout from '../../layouts/CustomerLayout';
import { useNavigate } from 'react-router-dom';
import { 
    createOrder,
    getProductById,
    getProductColorsByProductId,
    getSizesByProductColorId,
    getImagesByProductColorId
} from '../../services/home/HomeService';
import { formatPrice } from '../../utils/formatters';

const { Title, Text } = Typography;
const { TextArea } = Input;

const styles = {
    container: {
    padding: '24px',
    },
    steps: {
    marginBottom: '40px',
    },
    section: {
    marginBottom: '24px',
    },
    card: {
    borderRadius: '12px',
    boxShadow: '0 2px 8px rgba(0,0,0,0.06)',
    },
    title: {
    fontSize: '24px',
    marginBottom: '24px',
    color: '#001529',
    textAlign: 'center',
    },
    subtitle: {
    fontSize: '18px',
    marginBottom: '16px',
    color: '#001529',
    },
    formItem: {
    marginBottom: '16px',
    },
    input: {
    borderRadius: '8px',
    },
    orderSummary: {
    background: '#f8f9fa',
    padding: '20px',
    borderRadius: '12px',
    marginBottom: '20px',
    },
    orderItem: {
    marginBottom: '16px',
    padding: '12px',
    background: '#fff',
    borderRadius: '8px',
    boxShadow: '0 1px 3px rgba(0,0,0,0.05)',
    },
    itemImage: {
    width: '80px',
    height: '80px',
    objectFit: 'cover',
    borderRadius: '4px',
    },
    itemName: {
    fontWeight: 'bold',
    marginBottom: '4px',
    },
    itemDetails: {
    color: '#666',
    fontSize: '14px',
    },
    totalPrice: {
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#f50',
    textAlign: 'right',
    marginTop: '16px',
    },
    submitButton: {
    width: '100%',
    height: '48px',
    fontSize: '16px',
    background: 'linear-gradient(135deg, #001529 0%, #003a70 100%)',
    border: 'none',
    borderRadius: '8px',
    marginTop: '24px',
    },
    emptyCart: {
    textAlign: 'center',
    padding: '40px',
    },
    note: {
    fontSize: '14px',
    color: '#666',
    fontStyle: 'italic',
    marginTop: '8px',
    },
    };

const CheckoutPage = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [orderItems, setOrderItems] = useState([]);
    const [totalPrice, setTotalPrice] = useState(0);
    const [productDetails, setProductDetails] = useState({});

    useEffect(() => {
        const fetchOrderDetails = async () => {
            const cart = JSON.parse(localStorage.getItem('cart')) || [];
            if (cart.length === 0) {
                message.warning('Giỏ hàng của bạn đang trống');
                navigate('/home/cart');
                return;
            }

            try {
                const details = {};
                await Promise.all(cart.map(async (item) => {
                    // Get product details
                    const [product, colors] = await Promise.all([
                        getProductById(item.productId),
                        getProductColorsByProductId(item.productId)
                    ]);

                    const selectedColor = colors.find(color => color.productColorId === item.colorId);
                    
                    // Get sizes and images for the selected color
                    const [sizes, images] = await Promise.all([
                        getSizesByProductColorId(item.colorId),
                        getImagesByProductColorId(item.colorId)
                    ]);

                    const selectedSize = sizes.find(size => size.productSizeId === item.sizeId);
                    const mainImage = images[0]?.imageUrl;

                    details[item.productId] = {
                        ...product,
                        colorName: selectedColor?.colorName || 'N/A',
                        sizeValue: selectedSize?.sizeValue || 'N/A',
                        imageUrl: mainImage
                    };
                }));

                setProductDetails(details);
                setOrderItems(cart);
                const total = cart.reduce((acc, item) => acc + item.unitPrice * item.quantity, 0);
                setTotalPrice(total);
            } catch (error) {
                console.error('Error fetching order details:', error);
                message.error('Không thể tải thông tin đơn hàng');
            } finally {
                setLoading(false);
            }
        };

        fetchOrderDetails();
    }, [navigate]);

    const onFinish = async (values) => {
        setLoading(true);
        try {
            const orderData = {
                guestDto: {
                    fullName: values.fullName,
                    email: values.email,
                    phone: values.phone,
                    address: values.address,
                    address2: values.address2,
                    city: values.city,
                },
                orderDto: {
                    totalPrice: totalPrice,
                    isPaid: false,
                    orderNote: values.orderNote,
                },
                orderItemDtos: orderItems.map(item => ({
                    productSizeId: item.sizeId,
                    quantity: item.quantity,
                    price: item.unitPrice,
                })),
            };

            await createOrder(orderData);
            message.success('Đặt hàng thành công!');
            localStorage.removeItem('cart');
            navigate('/home/success');
        } catch (error) {
            message.error('Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại!');
            console.error('Error creating order:', error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <CustomerLayout>
                <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '400px' }}>
                    <Spin size="large" />
                </div>
            </CustomerLayout>
        );
    }

    if (orderItems.length === 0) {
        return (
            <CustomerLayout>
                <div style={styles.emptyCart}>
                    <ShoppingOutlined style={{ fontSize: '48px', color: '#001529' }} />
                    <Title level={3}>Giỏ hàng trống</Title>
                    <Button type="primary" onClick={() => navigate('/home')}>
                        Tiếp tục mua sắm
                    </Button>
                </div>
            </CustomerLayout>
        );
    }

    return (
        <CustomerLayout>
            <div style={styles.container}>
                <Steps
                    style={styles.steps}
                    items={[
                        {
                            title: 'Giỏ hàng',
                            status: 'finish',
                            icon: <ShoppingOutlined />,
                        },
                        {
                            title: 'Thông tin đặt hàng',
                            status: 'process',
                            icon: <UserOutlined />,
                        },
                        {
                            title: 'Hoàn tất',
                            status: 'wait',
                            icon: <CheckCircleOutlined />,
                        },
                    ]}
                />

                <Row gutter={24}>
                    <Col xs={24} lg={16}>
                        <Card style={styles.card}>
                            <Title level={3} style={styles.title}>
                                Thông tin đặt hàng
                            </Title>
                            <Form
                                form={form}
                                layout="vertical"
                                onFinish={onFinish}
                            >
                                <Row gutter={16}>
                                    <Col xs={24} md={12}>
                                        <Form.Item
                                            label="Họ và tên"
                                            name="fullName"
                                            rules={[{ required: true, message: 'Vui lòng nhập họ tên!' }]}
                                        >
                                            <Input size="large" style={styles.input} />
                                        </Form.Item>
                                    </Col>
                                    <Col xs={24} md={12}>
                                        <Form.Item
                                            label="Email"
                                            name="email"
                                            rules={[
                                                { required: true, message: 'Vui lòng nhập email!' },
                                                { type: 'email', message: 'Email không hợp lệ!' }
                                            ]}
                                        >
                                            <Input size="large" style={styles.input} />
                                        </Form.Item>
                                    </Col>
                                </Row>

                                <Row gutter={16}>
                                    <Col xs={24} md={12}>
                                        <Form.Item
                                            label="Số điện thoại"
                                            name="phone"
                                            rules={[{ required: true, message: 'Vui lòng nhập số điện thoại!' }]}
                                        >
                                            <Input size="large" style={styles.input} />
                                        </Form.Item>
                                    </Col>
                                    <Col xs={24} md={12}>
                                        <Form.Item
                                            label="Thành phố"
                                            name="city"
                                            rules={[{ required: true, message: 'Vui lòng nhập thành phố!' }]}
                                        >
                                            <Input size="large" style={styles.input} />
                                        </Form.Item>
                                    </Col>
                                </Row>

                                <Form.Item
                                    label="Địa chỉ"
                                    name="address"
                                    rules={[{ required: true, message: 'Vui lòng nhập địa chỉ!' }]}
                                >
                                    <Input size="large" style={styles.input} />
                                </Form.Item>

                                <Form.Item
                                    label="Địa chỉ bổ sung (tùy chọn)"
                                    name="address2"
                                >
                                    <Input size="large" style={styles.input} />
                                </Form.Item>

                                <Form.Item
                                    label="Ghi chú đơn hàng"
                                    name="orderNote"
                                >
                                    <TextArea rows={4} style={styles.input} />
                                </Form.Item>
                            </Form>
                        </Card>
                    </Col>

                    <Col xs={24} lg={8}>
                        <Card style={styles.card}>
                            <Title level={4} style={styles.subtitle}>
                                Đơn hàng của bạn
                            </Title>
                            <div style={styles.orderSummary}>
                                {orderItems.map((item, index) => {
                                    const details = productDetails[item.productId] || {};
                                    return (
                                        <div key={index} style={styles.orderItem}>
                                            <Row gutter={16} align="middle">
                                                <Col flex="80px">
                                                    <img
                                                        src={details.imageUrl || 'placeholder.jpg'}
                                                        alt={details.productName}
                                                        style={styles.itemImage}
                                                    />
                                                </Col>
                                                <Col flex="auto">
                                                    <div style={styles.itemName}>{details.productName}</div>
                                                    <div style={styles.itemDetails}>
                                                        Màu sắc: {details.colorName}
                                                    </div>
                                                    <div style={styles.itemDetails}>
                                                        Kích cỡ: {details.sizeValue}
                                                    </div>
                                                    <div style={styles.itemDetails}>
                                                        Số lượng: {item.quantity}
                                                    </div>
                                                    <div style={styles.itemDetails}>
                                                        {formatPrice(item.unitPrice)}
                                                    </div>
                                                </Col>
                                            </Row>
                                        </div>
                                    );
                                })}

                                <Divider />

                                <div style={styles.totalPrice}>
                                    Tổng cộng: {formatPrice(totalPrice)}
                                </div>
                            </div>

                            <Button
                                type="primary"
                                size="large"
                                onClick={() => form.submit()}
                                loading={loading}
                                style={styles.submitButton}
                                icon={<CreditCardOutlined />}
                            >
                                Đặt hàng
                            </Button>

                            <Text style={styles.note}>
                                * Bạn sẽ thanh toán khi nhận hàng (COD)
                            </Text>
                        </Card>
                    </Col>
                </Row>
            </div>
        </CustomerLayout>
    );
};

export default CheckoutPage;