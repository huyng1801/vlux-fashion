import React, { useState, useEffect } from 'react';
import { Layout, Badge, Button, Space } from 'antd';
import { Link } from 'react-router-dom';
import { ShoppingCartOutlined, LoginOutlined, UserAddOutlined, LogoutOutlined } from '@ant-design/icons';
import {jwtDecode} from 'jwt-decode';

const { Header: AntHeader } = Layout;

const styles = {
    header: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '0 50px',
        background: 'linear-gradient(135deg, #001529 0%, #003a70 100%)',
        boxShadow: '0 2px 8px rgba(0,0,0,0.15)',
        height: '64px',
        position: 'fixed',
        width: '100%',
        zIndex: 1000,
    },
    logo: {
        color: '#fff',
        fontSize: '28px',
        fontWeight: 'bold',
        fontFamily: "'Poppins', sans-serif",
        textShadow: '2px 2px 4px rgba(0,0,0,0.3)',
        letterSpacing: '1px',
        cursor: 'pointer',
        transition: 'all 0.3s ease',
    },
    rightSection: {
        display: 'flex',
        alignItems: 'center',
        gap: '24px',
    },
    button: {
        background: 'rgba(255, 255, 255, 0.1)',
        border: '1px solid rgba(255, 255, 255, 0.2)',
        color: '#fff',
        padding: '4px 15px',
        height: '36px',
        display: 'flex',
        alignItems: 'center',
        cursor: 'pointer',
        transition: 'all 0.3s ease',
        borderRadius: '8px',
    },
    cartButton: {
        background: 'rgba(255, 255, 255, 0.1)',
        border: '1px solid rgba(255, 255, 255, 0.2)',
        color: '#fff',
        fontSize: '20px',
        padding: '8px 15px',
        display: 'flex',
        alignItems: 'center',
        cursor: 'pointer',
        transition: 'all 0.3s ease',
        borderRadius: '8px',
    },
    badge: {
        backgroundColor: '#f50',
        boxShadow: '0 0 0 2px rgba(255, 85, 0, 0.1)',
    },
};

const Header = () => {
    const [cartCount, setCartCount] = useState(0);
    const [username, setUsername] = useState(null);  // Store the username if logged in

    useEffect(() => {
        // Initial cart count
        updateCartCount();

        // Listen for storage changes
        window.addEventListener('storage', updateCartCount);

        // Decode JWT token if available
        const token = localStorage.getItem('jwt'); // Assuming JWT is stored in localStorage
        if (token) {
            try {
                const decodedToken = jwtDecode(token);
                setUsername(decodedToken.username);  // Extract username from the token
            } catch (err) {
                console.error('Invalid token', err);
            }
        }

        // Cleanup listener
        return () => {
            window.removeEventListener('storage', updateCartCount);
        };
    }, []);

    const updateCartCount = () => {
        const cart = JSON.parse(localStorage.getItem('cart')) || [];
        const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
        setCartCount(totalItems);
    };

    const buttonHoverEffect = (e) => {
        e.currentTarget.style.background = 'rgba(255, 255, 255, 0.2)';
        e.currentTarget.style.transform = 'translateY(-2px)';
    };

    const buttonLeaveEffect = (e) => {
        e.currentTarget.style.background = 'rgba(255, 255, 255, 0.1)';
        e.currentTarget.style.transform = 'translateY(0)';
    };

    const handleLogout = () => {
        localStorage.removeItem('jwt'); // Remove token from localStorage
        setUsername(null); // Clear username state
        window.location.reload(); // Reload to reflect changes
    };

    return (
        <AntHeader style={styles.header}>
            <Link to="/">
                <div style={styles.logo}>EcoShop</div>
            </Link>
            <div style={styles.rightSection}>
                <Space size={16}>
                    {!username ? (
                        <>
                            <Link to="/login">
                                <Button 
                                    icon={<LoginOutlined />}
                                    style={styles.button}
                                    onMouseEnter={buttonHoverEffect}
                                    onMouseLeave={buttonLeaveEffect}
                                >
                                    Đăng nhập
                                </Button>
                            </Link>
                            <Link to="/register">
                                <Button 
                                    icon={<UserAddOutlined />}
                                    style={styles.button}
                                    onMouseEnter={buttonHoverEffect}
                                    onMouseLeave={buttonLeaveEffect}
                                >
                                    Đăng ký
                                </Button>
                            </Link>
                        </>
                    ) : (
                        <Button 
                            icon={<LogoutOutlined />}
                            style={styles.button}
                            onClick={handleLogout}
                            onMouseEnter={buttonHoverEffect}
                            onMouseLeave={buttonLeaveEffect}
                        >
                            Đăng xuất
                        </Button>
                    )}
                    <Link to="/cart">
                        <Badge 
                            count={cartCount} 
                            style={styles.badge}
                            offset={[-5, 5]}
                            showZero
                        >
                            <Button 
                                icon={<ShoppingCartOutlined />} 
                                style={styles.cartButton}
                                onMouseEnter={buttonHoverEffect}
                                onMouseLeave={buttonLeaveEffect}
                            />
                        </Badge>
                    </Link>
                </Space>
            </div>
        </AntHeader>
    );
};

export default Header;
