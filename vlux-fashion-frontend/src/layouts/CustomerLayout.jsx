import React from 'react';
import { Layout, Menu, Breadcrumb, Input, Button, Badge } from 'antd';
import { Link } from 'react-router-dom';
import { SearchOutlined, ShoppingCartOutlined, UserOutlined } from '@ant-design/icons';
import './CustomerLayout.css'; // Custom styles

const { Header, Content, Footer } = Layout;

const CustomerLayout = ({ children }) => {
  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header className="header">
        <div className="logo">EcoShop</div>
        <div className="search-bar">
       
          <Link to="/home/cart">
            <Badge count={5} style={{ marginLeft: '10px' }}>
              <Button icon={<ShoppingCartOutlined />} className="cart-button" />
            </Badge>
          </Link>

        </div>
      </Header>

      <Menu
        theme="dark"
        mode="horizontal"
        defaultSelectedKeys={['1']}
        className="menu"
      >
        <Menu.Item key="1">
          <Link to="/home">Trang Chủ</Link>
        </Menu.Item>
        <Menu.Item key="2">
          <Link to="home/product">Sản Phẩm</Link>
        </Menu.Item>
        <Menu.Item key="3">
          <Link to="/about">Giới Thiệu</Link>
        </Menu.Item>
        <Menu.Item key="4">
          <Link to="/contact">Liên Hệ</Link>
        </Menu.Item>
      </Menu>

      <Content style={{ padding: '0 50px' }}>
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item><Link to="/">Trang Chủ</Link></Breadcrumb.Item>
          <Breadcrumb.Item>Sản Phẩm</Breadcrumb.Item>
        </Breadcrumb>
        <div className="site-layout-content">{children}</div>
      </Content>

      <Footer className="footer">
        EcoShop ©2024 Created by Your Name
      </Footer>
    </Layout>
  );
};

export default CustomerLayout;
