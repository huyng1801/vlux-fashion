import React, { useState, useEffect } from 'react';
import { Table, Button, message, Image, Modal, Form, Input, Switch } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons'; // Import icons
import BannerService from '../../services/admin/BannerService'; // Ensure your API service is correctly imported

const BannerList = () => {
  const [banners, setBanners] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [currentBanner, setCurrentBanner] = useState(null);
  const [form] = Form.useForm();
  const [imageFile, setImageFile] = useState(null); // State for the file input
  const [loadingButton, setLoadingButton] = useState(false); // State for loading button actions

  useEffect(() => {
    loadBanners();
  }, []);

  const loadBanners = () => {
    setLoading(true);
    BannerService.getAllBanners()
      .then((response) => {
        setBanners(response);
      })
      .catch(() => {
        message.error('Lỗi khi tải danh sách banner');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleDelete = (id) => {
    // Trigger confirmation dialog before deleting
    Modal.confirm({
      title: 'Bạn có chắc chắn muốn xóa banner này không?',
      content: 'Hành động này không thể hoàn tác.',
      okText: 'Xóa',
      okType: 'danger',
      cancelText: 'Hủy',
      onOk() {
        BannerService.deleteBanner(id)
          .then(() => {
            message.success('Xóa banner thành công');
            loadBanners();
          })
          .catch(() => {
            message.error('Lỗi khi xóa banner');
          });
      },
      onCancel() {
        console.log('Hủy hành động xóa');
      },
    });
  };

  const showModal = (banner) => {
    setCurrentBanner(banner);
    form.setFieldsValue(banner);
    setIsModalVisible(true);
    setImageFile(null); // Reset the file input when opening the modal
  };

  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {
        const data = { ...values, imageFile }; // Include the file in the request
        setLoadingButton(true); // Set loading state for the button
        if (currentBanner) {
          // Update existing banner
          BannerService.updateBanner(currentBanner.bannerId, data)
            .then(() => {
              message.success('Cập nhật banner thành công');
              setIsModalVisible(false);
              loadBanners();
            })
            .catch(() => {
              message.error('Lỗi khi cập nhật banner');
            })
            .finally(() => {
              setLoadingButton(false); // Reset loading state for the button
            });
        } else {
          // Create new banner
          BannerService.createBanner(data)
            .then(() => {
              message.success('Tạo banner thành công');
              setIsModalVisible(false);
              loadBanners();
            })
            .catch(() => {
              message.error('Lỗi khi tạo banner');
            })
            .finally(() => {
              setLoadingButton(false); // Reset loading state for the button
            });
        }
      })
      .catch((errorInfo) => {
        console.log('Failed:', errorInfo);
      });
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setCurrentBanner(null);
    form.resetFields();
    setImageFile(null); // Reset the image file
  };

  const handleFileChange = (e) => {
    setImageFile(e.target.files[0]); // Store the file
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'bannerId',
      key: 'bannerId',
    },
    {
      title: 'Tiêu đề',
      dataIndex: 'title',
      key: 'title',
    },
    {
      title: 'Liên kết',
      dataIndex: 'link',
      key: 'link',
    },
    {
      title: 'Hình ảnh',
      dataIndex: 'imageUrl',
      key: 'imageUrl',
      render: (imageUrl) => (
        <Image
          width={150}
          src={imageUrl}
          alt="Hình ảnh banner"
          placeholder={<Image preview={false} src="loading.gif" />}
        />
      ),
    },
    {
      title: 'Hiển thị',
      dataIndex: 'isVisible',
      key: 'isVisible',
      render: (visible) => (visible ? 'Có' : 'Không'),
    },
    {
      title: 'Hành động',
      key: 'actions',
      render: (text, record) => (
        <span>
          <Button
            type="link"
            icon={<EditOutlined />}
            onClick={() => showModal(record)}
          >
          </Button>
          <Button
            type="link"
            danger
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.bannerId)}
          >
          </Button>
        </span>
      ),
    },
  ];
  
  return (
    <>
      <Button type="primary" onClick={() => showModal(null)} style={{ marginBottom: 16 }}>
        Thêm Banner
      </Button>
      <Table
        dataSource={banners}
        columns={columns}
        rowKey="bannerId"
        loading={loading}
        pagination={{ pageSize: 8 }} 
      />
      <Modal
        title={currentBanner ? 'Chỉnh sửa Banner' : 'Thêm Banner'}
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        confirmLoading={loadingButton} // Show loading state on OK button
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="title"
            label="Tiêu đề"
            rules={[{ required: true, message: 'Vui lòng nhập tiêu đề!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name="link"
            label="Liên kết"
            rules={[{ required: true, message: 'Vui lòng nhập liên kết!' }]}
          >
            <Input />
          </Form.Item>
          <Form.Item label="Hình ảnh">
            <Input type="file" accept="image/*" onChange={handleFileChange} />
            {imageFile && <p style={{ marginTop: 10 }}>Selected file: {imageFile.name}</p>} {/* Show selected file name */}
          </Form.Item>
          <Form.Item
            name="isVisible"
            label="Hiển thị"
            valuePropName="checked"
          >
            <Switch checked={form.getFieldValue('isVisible')} onChange={(checked) => form.setFieldsValue({ isVisible: checked })} />
          </Form.Item>
        </Form>
      </Modal>
    </>
  );
};

export default BannerList;
