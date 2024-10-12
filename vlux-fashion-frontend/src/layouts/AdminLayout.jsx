import React from 'react';
import { Layout, Menu, Breadcrumb } from 'antd';
import { Link } from 'react-router-dom';
import {
  HomeOutlined,
  FlagOutlined,
  TagOutlined,
  FolderOutlined,
  FolderOpenOutlined,
  AppstoreAddOutlined, // Changed to AppstoreAddOutlined for products
} from '@ant-design/icons'; // Importing the icons
import './AdminLayout.css'; // Assuming your custom styles

const { Header, Content, Sider } = Layout;

const AdminLayout = ({ children }) => {
  return (
    <Layout style={{ minHeight: '80vh' }}>
      <Sider width={250} className="site-layout-background">
        <div className="logo">
          <img src="https://odysseyhouse.com.au/wp-content/uploads/2019/08/Profile-Photo-Place-Holder.png" alt="Profile" />
          <span className="logo_name">Huy Nguyễn</span>
        </div>
        <Menu
          mode="inline"
          defaultSelectedKeys={['1']}
          style={{ height: '100%', borderRight: 0 }}
        >
          <Menu.Item key="1" icon={<HomeOutlined />}>
            <Link to="/">Trang chủ</Link>
          </Menu.Item>
          <Menu.Item key="2" icon={<FlagOutlined />}>
            <Link to="/banners">Banner</Link>
          </Menu.Item>
          <Menu.Item key="3" icon={<TagOutlined />}>
            <Link to="/brands">Thương hiệu</Link>
          </Menu.Item>
          <Menu.Item key="4" icon={<FolderOutlined />}>
            <Link to="/categories">Danh mục</Link>
          </Menu.Item>
          <Menu.Item key="5" icon={<FolderOpenOutlined />}>
            <Link to="/subcategories">Danh mục con</Link>
          </Menu.Item>
          <Menu.Item key="6" icon={<AppstoreAddOutlined />}>
            <Link to="/products">Sản phẩm</Link>
          </Menu.Item>
          <Menu.Item key="7" icon={<AppstoreAddOutlined />}>
            <Link to="/orders">Đơn hàng</Link>
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout>
        <Header className="header">
          <div className="logo">
            <span className="admin_name">Huy Nguyễn</span>
          </div>
        </Header>
        <Content style={{ margin: '0 16px' }}>
          <Breadcrumb style={{ margin: '16px 0' }}>
            <Breadcrumb.Item>Home</Breadcrumb.Item>
            <Breadcrumb.Item>Admin</Breadcrumb.Item>
          </Breadcrumb>
          <div className="site-layout-content">{children}</div>
        </Content>
      </Layout>
    </Layout>
  );
};

export default AdminLayout;
