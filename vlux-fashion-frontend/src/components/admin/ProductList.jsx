import React, { useState, useEffect } from 'react';
import ProductService from '../../services/admin/ProductService';
import SubcategoryService from '../../services/admin/SubcategoryService';
import BrandService from '../../services/admin/BrandService';
import { Table, Button, message, Modal, Form, Input, Select, Switch } from 'antd';
import ProductColorModal from './ProductColorModal'; // Import component quản lý màu sắc sản phẩm

const { Option } = Select;

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [subcategories, setSubcategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);
  const [form] = Form.useForm();
  const [colorModalVisible, setColorModalVisible] = useState(false);

  useEffect(() => {
    loadBrands();
    loadProducts();
    loadSubcategories();
  }, []);

  const loadSubcategories = () => {
    SubcategoryService.getAllSubcategories(null, null)
      .then((response) => {
        setSubcategories(response.data);
      })
      .catch(() => {
        message.error("Lỗi khi tải danh mục con");
      });
  };

  const loadBrands = () => {
    BrandService.getAllBrands()
      .then((response) => {
        setBrands(response.data);
      })
      .catch(() => {
        message.error("Lỗi khi tải thương hiệu");
      });
  };

  const loadProducts = () => {
    ProductService.getAllProducts()
      .then((response) => {
        setProducts(response.data);
      })
      .catch(() => {
        message.error("Lỗi khi tải sản phẩm");
      });
  };

  const handleDelete = (id) => {
    ProductService.deleteProduct(id)
      .then(() => {
        message.success("Xóa sản phẩm thành công");
        loadProducts();
      })
      .catch(() => {
        message.error("Lỗi khi xóa sản phẩm");
      });
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

  const handleAdd = () => {
    setEditingProduct(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  const handleEdit = (product) => {
    setEditingProduct(product);
    form.setFieldsValue({
      productName: product.productName,
      subCategoryId: product.subCategoryId,
      brandId: product.brandId,
      originalPrice: product.originalPrice,
      unitPrice: product.unitPrice,
      isVisible: product.isVisible,
    });
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
  };

  const handleOk = () => {
    form.validateFields()
      .then((values) => {
        const productData = { ...values };

        if (editingProduct) {
          ProductService.updateProduct(editingProduct.productId, productData)
            .then(() => {
              message.success("Cập nhật sản phẩm thành công");
              loadProducts();
            })
            .catch(() => {
              message.error("Lỗi khi cập nhật sản phẩm");
            });
        } else {
          ProductService.createProduct(productData)
            .then(() => {
              message.success("Thêm sản phẩm thành công");
              loadProducts();
            })
            .catch(() => {
              message.error("Lỗi khi thêm sản phẩm");
            });
        }
        setIsModalVisible(false);
        form.resetFields();
      })
      .catch(() => {
        message.error("Vui lòng điền đầy đủ các trường bắt buộc");
      });
  };

  const handleShowColors = (product) => {
    setEditingProduct(product); // Đặt sản phẩm để quản lý màu sắc
    setColorModalVisible(true); // Mở modal quản lý màu sắc
  };

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
      render: (text) => (
        <span>{text ? 'Hiển thị' : 'Ẩn'}</span>
      ),
    },
    {
      title: 'Hành động',
      render: (text, record) => (
        <span>
          <Button type="link" onClick={() => handleEdit(record)}>Sửa</Button>
          <Button type="link" onClick={() => showDeleteConfirm(record.productId)}>Xóa</Button>
          <Button type="link" onClick={() => handleShowColors(record)}>Quản lý màu sắc</Button>
        </span>
      ),
    },
  ];

  return (
    <div>
      <Button type="primary" onClick={handleAdd}>Thêm sản phẩm</Button>
      <Table columns={columns} dataSource={products} rowKey="productId"  pagination={{ pageSize: 8 }} />

      <Modal
        title={editingProduct ? "Sửa sản phẩm" : "Thêm sản phẩm"}
        visible={isModalVisible}
        onCancel={handleCancel}
        onOk={handleOk}
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
          <Form.Item name="isVisible" label="Hiển thị">
            <Switch />
          </Form.Item>
        </Form>
      </Modal>

      {/* Modal quản lý màu sắc sản phẩm */}
      <ProductColorModal
        product={editingProduct}
        onCancel={() => setEditingProduct(null)}
        reloadColors={() => loadProducts()} // Tải lại màu sắc sau khi thêm/xóa
      />
    </div>
  );
};

export default ProductList;
