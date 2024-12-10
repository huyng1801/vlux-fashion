import React, { useState, useEffect } from 'react';
import { Layout, Badge, Button } from 'antd';
import { Link } from 'react-router-dom';
import { ShoppingCartOutlined } from '@ant-design/icons';

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
    searchBar: {
        display: 'flex',
        alignItems: 'center',
        gap: '24px',
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

    useEffect(() => {
        // Initial cart count
        updateCartCount();

        // Listen for storage changes
        window.addEventListener('storage', updateCartCount);

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

    return (
        <AntHeader style={styles.header}>
            <Link to="/home">
                <div style={styles.logo}>EcoShop</div>
            </Link>
            <div style={styles.searchBar}>
                <Link to="/home/cart">
                    <Badge 
                        count={cartCount} 
                        style={styles.badge}
                        offset={[-5, 5]}
                        showZero
                    >
                        <Button 
                            icon={<ShoppingCartOutlined />} 
                            style={styles.cartButton}
                            onMouseEnter={(e) => {
                                e.currentTarget.style.background = 'rgba(255, 255, 255, 0.2)';
                                e.currentTarget.style.transform = 'translateY(-2px)';
                            }}
                            onMouseLeave={(e) => {
                                e.currentTarget.style.background = 'rgba(255, 255, 255, 0.1)';
                                e.currentTarget.style.transform = 'translateY(0)';
                            }}
                        />
                    </Badge>
                </Link>
            </div>
        </AntHeader>
    );
};

export default Header;