import React from "react";
import { Layout, Menu, Button, Dropdown } from "antd";
import { Link } from "react-router-dom";
import {
  HomeOutlined,
  FlagOutlined,
  TagOutlined,
  FolderOutlined,
  FolderOpenOutlined,
  AppstoreAddOutlined,
  UserOutlined,
  LogoutOutlined,
} from "@ant-design/icons";
import { useAuth } from '../contexts/AuthContext';
import ProtectedRoute from "../components/admin/ProtectedRoute";

const { Header, Content, Sider } = Layout;

const styles = {
  layout: {
    minHeight: "100vh",
  },
  sider: {
    background: "#fff",
  },
  logo: {
    height: "64px",
    padding: "16px",
    display: "flex",
    alignItems: "center",
    gap: "12px",
    background: "#001529",
  },
  logoImage: {
    width: "32px",
    height: "32px",
    borderRadius: "50%",
  },
  logoName: {
    color: "#fff",
    fontSize: "16px",
    fontWeight: "bold",
  },
  menu: {
    height: "calc(100vh - 64px)",
    borderRight: 0,
  },
  header: {
    padding: "0 24px",
    background: "#fff",
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
  },
  userMenu: {
    display: "flex",
    alignItems: "center",
    gap: "8px",
    cursor: "pointer",
  },
  userName: {
    fontSize: "16px",
    fontWeight: "bold",
  },
  content: {
    margin: "24px 16px",
  },
};

const AdminLayout = ({ children }) => {
  const { user, handleLogout } = useAuth();

  const userMenuItems = [
    {
      key: "logout",
      icon: <LogoutOutlined />,
      label: "Đăng xuất",
      onClick: handleLogout,
    },
  ];

  return (
    <ProtectedRoute>
      <Layout style={styles.layout}>
        <Sider width={250} style={styles.sider}>
          <div style={styles.logo}>
            <img
              src="https://odysseyhouse.com.au/wp-content/uploads/2019/08/Profile-Photo-Place-Holder.png"
              alt="Profile"
              style={styles.logoImage}
            />
            <span style={styles.logoName}>{user?.name || "Admin"}</span>
          </div>
          <Menu mode="inline" defaultSelectedKeys={["1"]} style={styles.menu}>
            <Menu.Item key="1" icon={<HomeOutlined />}>
              <Link to="/admin/dashboard">Trang chủ</Link>
            </Menu.Item>
            <Menu.Item key="2" icon={<FlagOutlined />}>
              <Link to="/admin/banner">Banner</Link>
            </Menu.Item>
            <Menu.Item key="3" icon={<TagOutlined />}>
              <Link to="/admin/brand">Thương hiệu</Link>
            </Menu.Item>
            <Menu.Item key="4" icon={<FolderOutlined />}>
              <Link to="/admin/category">Danh mục</Link>
            </Menu.Item>
            <Menu.Item key="5" icon={<FolderOpenOutlined />}>
              <Link to="/admin/subcategory">Danh mục con</Link>
            </Menu.Item>
            <Menu.Item key="6" icon={<AppstoreAddOutlined />}>
              <Link to="/admin/product">Sản phẩm</Link>
            </Menu.Item>
            <Menu.Item key="7" icon={<AppstoreAddOutlined />}>
              <Link to="/admin/order">Đơn hàng</Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout>
          <Header style={styles.header}>
            <div style={styles.userName}>{user?.name || "Admin"}</div>
            <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
              <div style={styles.userMenu}>
                <UserOutlined />
                <span>{user?.name || "Admin"}</span>
              </div>
            </Dropdown>
          </Header>
          <Content style={styles.content}>{children}</Content>
        </Layout>
      </Layout>
    </ProtectedRoute>
  );
};

export default AdminLayout;