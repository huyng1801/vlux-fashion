import React, { useState, useEffect } from 'react';
import ProductService from '../../services/admin/ProductService';
import SubcategoryService from '../../services/admin/SubcategoryService';
import BrandService from '../../services/admin/BrandService';
import { Table, Button, message, Modal, Form, Input, Select, Switch } from 'antd';
import ProductColorModal from './ProductColorModal';
import { EditOutlined, DeleteOutlined, AppstoreAddOutlined } from '@ant-design/icons';

const { Option } = Select;

const ProductList = () => {
  // State management
  const [products, setProducts] = useState([]);
  const [subcategories, setSubcategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [modalStates, setModalStates] = useState({
    productModal: false,
    colorModal: false
  });
  const [editingProduct, setEditingProduct] = useState(null);
  const [selectedProduct, setSelectedProduct] = useState(null); 
  const [form] = Form.useForm();

  // Initial data loading
  useEffect(() => {
    loadInitialData();
  }, []);

  const loadInitialData = async () => {
    try {
      await Promise.all([
        loadBrands(),
        loadProducts(),
        loadSubcategories()
      ]);
    } catch (error) {
      message.error("Lỗi khi tải dữ liệu ban đầu");
    }
  };

   // Modal handling
   const toggleModal = (modalType, visible, product = null) => {
    setModalStates(prev => ({
      ...prev,
      [modalType]: visible
    }));
    
    if (modalType === 'productModal') {
      setEditingProduct(product);
      if (product) {
        form.setFieldsValue({
          productName: product.productName,
          subCategoryId: product.subCategoryId,
          brandId: product.brandId,
          originalPrice: product.originalPrice,
          unitPrice: product.unitPrice,
          isVisible: product.isVisible,
        });
      } else {
        form.resetFields();
      }
    } else if (modalType === 'colorModal') {
      setSelectedProduct(product); // Set selected product for color modal
    }
  };
  // Data loading functions
  const loadSubcategories = async () => {
    try {
      const response = await SubcategoryService.getAllSubcategories(null, null);
      setSubcategories(response);
    } catch (error) {
      message.error("Lỗi khi tải danh mục con");
    }
  };

  const loadBrands = async () => {
    try {
      const response = await BrandService.getAllBrands();
      setBrands(response);
    } catch (error) {
      message.error("Lỗi khi tải thương hiệu");
    }
  };

  const loadProducts = async () => {
    try {
      const response = await ProductService.getAllProducts();
      setProducts(response);
    } catch (error) {
      message.error("Lỗi khi tải sản phẩm");
    }
  };

  // Product operations
  const handleProductSubmit = async () => {
    try {
      const values = await form.validateFields();
      const productData = { ...values };

      if (editingProduct) {
        await ProductService.updateProduct(editingProduct.productId, productData);
        message.success("Cập nhật sản phẩm thành công");
      } else {
        await ProductService.createProduct(productData);
        message.success("Thêm sản phẩm thành công");
      }

      toggleModal('productModal', false);
      loadProducts();
    } catch (error) {
      message.error("Vui lòng điền đầy đủ các trường bắt buộc");
    }
  };

  const handleDelete = async (id) => {
    try {
      await ProductService.deleteProduct(id);
      message.success("Xóa sản phẩm thành công");
      loadProducts();
    } catch (error) {
      message.error("Lỗi khi xóa sản phẩm");
    }
  };

  const showDeleteConfirm = (id) => {
    Modal.confirm({
      title: 'Bạn có chắc chắn muốn xóa sản phẩm này không?',
      content: 'Hành động này không thể hoàn tác.',
      okText: 'Xóa',
      okType: 'danger',
      cancelText: 'Hủy',
      onOk() {
        handleDelete(id);
      },
    });
  };

  // Table columns configuration
  const columns = [
    {
      title: 'ID',
      dataIndex: 'productId',
      key: 'productId',
    },
    {
      title: 'Tên sản phẩm',
      dataIndex: 'productName',
      key: 'productName',
    },
    {
      title: 'Danh mục con',
      dataIndex: 'subCategoryName',
      key: 'subCategoryName',
    },
    {
      title: 'Thương hiệu',
      dataIndex: 'brandName',
      key: 'brandName',
    },
    {
      title: 'Giá gốc',
      dataIndex: 'originalPrice',
      key: 'originalPrice',
      render: (text) => <span>{text.toLocaleString()} VNĐ</span>,
    },
    {
      title: 'Giá bán',
      dataIndex: 'unitPrice',
      key: 'unitPrice',
      render: (text) => <span>{text.toLocaleString()} VNĐ</span>,
    },
    {
      title: 'Trạng thái',
      dataIndex: 'isVisible',
      key: 'isVisible',
      render: (text) => <span>{text ? 'Hiển thị' : 'Ẩn'}</span>,
    },
    {
      title: 'Hành động',
      render: (_, record) => (
        <span>
          <Button 
            type="link" 
            icon={<EditOutlined />} 
            onClick={() => toggleModal('productModal', true, record)}
          />
          <Button 
            type="link" 
            icon={<DeleteOutlined />} 
            danger 
            onClick={() => showDeleteConfirm(record.productId)}
          />
          <Button 
            type="link" 
            icon={<AppstoreAddOutlined />} 
            onClick={() => toggleModal('colorModal', true, record)}
          />
        </span>
      ),
    },
  ];

  return (
    <div>
      <Button 
        type="primary" 
        onClick={() => toggleModal('productModal', true)}
        style={{ marginBottom: 16 }}
      >
        Thêm sản phẩm
      </Button>

      <Table 
        columns={columns} 
        dataSource={products} 
        rowKey="productId" 
        pagination={{ pageSize: 8 }} 
      />

      {/* Product Modal */}
      <Modal
        title={editingProduct ? "Sửa sản phẩm" : "Thêm sản phẩm"}
        open={modalStates.productModal}
        onCancel={() => toggleModal('productModal', false)}
        onOk={handleProductSubmit}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="productName"
            label="Tên sản phẩm"
            rules={[{ required: true, message: 'Vui lòng nhập tên sản phẩm!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="subCategoryId"
            label="Danh mục con"
            rules={[{ required: true, message: 'Vui lòng chọn danh mục con!' }]}
          >
            <Select>
              {subcategories.map((subcategory) => (
                <Option key={subcategory.subCategoryId} value={subcategory.subCategoryId}>
                  {subcategory.subCategoryName}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="brandId"
            label="Thương hiệu"
            rules={[{ required: true, message: 'Vui lòng chọn thương hiệu!' }]}
          >
            <Select>
              {brands.map((brand) => (
                <Option key={brand.brandId} value={brand.brandId}>
                  {brand.brandName}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="originalPrice"
            label="Giá gốc"
            rules={[{ required: true, message: 'Vui lòng nhập giá gốc!' }]}
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item
            name="unitPrice"
            label="Giá bán"
            rules={[{ required: true, message: 'Vui lòng nhập giá bán!' }]}
          >
            <Input type="number" />
          </Form.Item>
          <Form.Item name="isVisible" label="Hiển thị" valuePropName="checked">
            <Switch />
          </Form.Item>
        </Form>
      </Modal>

      {/* Color Modal */}
      <ProductColorModal
        visible={modalStates.colorModal}
        product={selectedProduct}
        onCancel={() => toggleModal('colorModal', false)}
        onSuccess={loadProducts}
      />
    </div>
  );
};

export default ProductList;