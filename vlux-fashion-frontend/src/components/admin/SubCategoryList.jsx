import React, { useState, useEffect } from 'react';
import SubcategoryService from '../../services/admin/SubcategoryService';
import CategoryService from '../../services/admin/CategoryService';
import { Table, Button, message, Modal, Form, Input, Select } from 'antd';

const { Option } = Select;

const SubCategoryList = () => {
  const [subcategories, setSubcategories] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedGender, setSelectedGender] = useState(null); // New state for gender filter
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingSubcategory, setEditingSubcategory] = useState(null);
  const [form] = Form.useForm();

  useEffect(() => {
    loadCategories(); // Load categories for the filter
    loadSubcategories(); // Load subcategories initially
  }, []);

  const loadCategories = () => {
    CategoryService.getAllCategories()
      .then((response) => {
        setCategories(response.data); // Load categories
      })
      .catch(() => {
        message.error("Lỗi khi tải danh mục");
      });
  };

  const loadSubcategories = (categoryId = null, gender = null) => {
    SubcategoryService.getAllSubcategories(categoryId, gender) // Pass the gender filter
      .then((response) => {
        setSubcategories(response.data);
      })
      .catch(() => {
        message.error("Lỗi khi tải danh mục phụ");
      });
  };

  const handleDelete = (id) => {
    SubcategoryService.deleteSubcategory(id)
      .then(() => {
        message.success("Xóa danh mục phụ thành công");
        loadSubcategories(selectedCategory, selectedGender); // Reload based on the selected category and gender
      })
      .catch(() => {
        message.error("Lỗi khi xóa danh mục phụ");
      });
  };

  const showDeleteConfirm = (id) => {
    Modal.confirm({
      title: 'Bạn có chắc chắn muốn xóa danh mục phụ này?',
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
    setEditingSubcategory(null);
    setIsModalVisible(true);
  };

  const handleEdit = (subcategory) => {
    setEditingSubcategory(subcategory);
    form.setFieldsValue({
      subcategoryName: subcategory.subCategoryName,
      categoryId: subcategory.categoryId, // Set the categoryId directly from subcategory
      gender: subcategory.gender,
    });
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        if (editingSubcategory) {
          // Update subcategory
          SubcategoryService.updateSubcategory(editingSubcategory.subCategoryId, values)
            .then(() => {
              message.success("Cập nhật danh mục phụ thành công");
              loadSubcategories(selectedCategory, selectedGender); // Reload subcategories for the selected category
            })
            .catch(() => {
              message.error("Tên danh mục phụ đã tồn tại");
            });
        } else {
          // Create new subcategory
          SubcategoryService.createSubcategory(values)
            .then(() => {
              message.success("Tạo danh mục phụ mới thành công");
              loadSubcategories(selectedCategory, selectedGender); // Reload subcategories for the selected category
            })
            .catch(() => {
              message.error("Tên danh mục phụ đã tồn tại");
            });
        }
        setIsModalVisible(false);
        form.resetFields();
      })
      .catch(() => {
        message.error("Vui lòng điền đầy đủ thông tin");
      });
  };

  const handleCategoryChange = (value) => {
    setSelectedCategory(value);
    loadSubcategories(value, selectedGender); // Load subcategories based on the selected category and gender
  };

  const handleGenderChange = (value) => {
    setSelectedGender(value);
    loadSubcategories(selectedCategory, value); // Load subcategories based on the selected category and gender
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'subCategoryId',
      key: 'subCategoryId',
    },
    {
      title: 'Tên danh mục phụ',
      dataIndex: 'subCategoryName',
      key: 'subCategoryName',
    },
    {
      title: 'Danh mục chính',
      dataIndex: 'categoryName',
      key: 'categoryName',
    },
    {
      title: 'Giới tính',
      dataIndex: 'gender',
      key: 'gender',
      render: (text) => <span>{text === 'MALE' ? 'Nam' : 'Nữ'}</span>,
    },
    {
      title: 'Thao tác',
      render: (text, record) => (
        <span>
          <Button type="link" onClick={() => handleEdit(record)}>Sửa</Button>
          <Button type="link" danger onClick={() => showDeleteConfirm(record.subCategoryId)}>Xóa</Button>
        </span>
      ),
      key: 'actions',
    },
  ];

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '20px' }}>
        <Button type="primary" onClick={handleAdd}>
          Thêm danh mục phụ
        </Button>
        <div style={{ display: 'flex', justifyContent: 'space-between', gap: '20px' }}>
            <Select
            placeholder="Chọn danh mục chính"
            style={{ width: 200 }}
            onChange={handleCategoryChange}
            allowClear
            >
            <Option key={null} value={null}>
                Tất cả
            </Option>
            {categories.map((category) => (
                <Option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
                </Option>
            ))}
            </Select>
                {/* Gender Filter Dropdown */}
            <Select
            placeholder="Chọn giới tính"
            style={{ width: 200 }}
            onChange={handleGenderChange}
            allowClear
            >
            <Option value={null}>Tất cả</Option>
            <Option value="MALE">Nam</Option>
            <Option value="FEMALE">Nữ</Option>
            </Select>
        </div>
      </div>



      <Table dataSource={subcategories} columns={columns} rowKey="subCategoryId"  pagination={{ pageSize: 8 }} />

      <Modal
        title={editingSubcategory ? "Sửa danh mục phụ" : "Thêm danh mục phụ"}
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Form form={form} layout="vertical">
          <Form.Item
            label="Tên danh mục phụ"
            name="subCategoryName"
            rules={[{ required: true, message: 'Vui lòng nhập tên danh mục phụ' }]}
          >
            <Input placeholder="Nhập tên danh mục phụ" />
          </Form.Item>
          <Form.Item
            label="Danh mục chính"
            name="categoryId"
            rules={[{ required: true, message: 'Vui lòng chọn danh mục chính' }]}
          >
            <Select placeholder="Chọn danh mục chính">
              {categories.map((category) => (
                <Option key={category.categoryId} value={category.categoryId}>
                  {category.categoryName}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            label="Giới tính"
            name="gender"
            rules={[{ required: true, message: 'Vui lòng chọn giới tính' }]}
          >
            <Select placeholder="Chọn giới tính">
              <Option value="MALE">Nam</Option>
              <Option value="FEMALE">Nữ</Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default SubCategoryList;
