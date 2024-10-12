import React, { useState, useEffect } from 'react';
import { Modal, Form, Input, Table, Button, message, Upload } from 'antd';
import ColorService from '../../services/admin/ColorService';
import SizeService from '../../services/admin/SizeService';
import ProductColorImageService from '../../services/admin/ProductColorImageService';
import { UploadOutlined } from '@ant-design/icons';

const ProductColorModal = ({ product, onCancel, reloadColors }) => {
  const [colorForm] = Form.useForm();
  const [sizeForm] = Form.useForm();
  const [imageForm] = Form.useForm();
  const [productColors, setProductColors] = useState([]);
  const [productSizes, setProductSizes] = useState([]);
  const [productColorImages, setProductColorImages] = useState([]);
  const [selectedColorId, setSelectedColorId] = useState(null);

  useEffect(() => {
    if (product) {
      loadProductColors(product.productId);
    }
  }, [product]);

  const loadProductColors = (productId) => {
    ColorService.getColorsByProductId(productId)
      .then((data) => {
        setProductColors(data);
      })
      .catch(() => {
        message.error("Lỗi khi tải màu sắc sản phẩm");
      });
  };

  const loadProductSizes = (colorId) => {
    SizeService.findByProductColorId(colorId)
      .then((data) => {
        setProductSizes(data);
      })
      .catch(() => {
        message.error("Lỗi khi tải kích thước sản phẩm");
      });
  };

  const loadProductColorImages = (colorId) => {
    ProductColorImageService.getImagesByColorId(colorId)
      .then((data) => {
   
        setProductColorImages(data);
      })
      .catch(() => {
        message.error("Lỗi khi tải hình ảnh sản phẩm");
      });
  };

  const handleColorSelect = (colorId) => {
    setSelectedColorId(colorId);
    loadProductSizes(colorId);
    loadProductColorImages(colorId);
  };

  const handleAddSize = () => {
    sizeForm.validateFields()
      .then((sizeData) => {
        const newSize = {
          sizeValue: sizeData.sizeValue,
          stockQuantity: sizeData.stockQuantity,
          productColorId: selectedColorId,
        };
  
        SizeService.createProductSize(newSize)
          .then((response) => {
            loadProductSizes(selectedColorId);
            sizeForm.resetFields();
            message.success("Kích thước đã được thêm thành công!");
          })
          .catch((error) => {
            console.error(error);
            message.error("Đã xảy ra lỗi khi thêm kích thước. Vui lòng thử lại!");
          });
      })
      .catch(() => {
        message.error("Vui lòng nhập thông tin hợp lệ");
      });
  };
  
  const handleAddColor = () => {
    colorForm.validateFields()
      .then((values) => {
        const { colorName, imageFile } = values;
        const formData = new FormData();
        formData.append('imageFile', imageFile[0].originFileObj);
        formData.append('colorName', colorName);
        formData.append('productId', product.productId);

        ColorService.addColorToProduct(formData)
          .then(() => {
            message.success("Thêm màu sắc thành công");
            loadProductColors(product.productId);
            colorForm.resetFields();
          })
          .catch((error) => {
            message.error(`Lỗi khi thêm màu sắc: ${error.response?.data?.message || error.message}`);
          });
      })
      .catch(() => {
        message.error("Vui lòng nhập thông tin hợp lệ");
      });
  };

  const handleDeleteColor = (colorId) => {
    ColorService.deleteColorFromProduct(colorId)
      .then(() => {
        message.success("Xóa màu sắc thành công");
        loadProductColors(product.productId);
      })
      .catch((error) => {
        message.error(`Lỗi khi xóa màu sắc: ${error.response?.data?.message || error.message}`);
      });
  };

  // Function to handle size deletion
  const handleDeleteSize = (productSizeId) => {
    SizeService.deleteProductSize(productSizeId) // Ensure you have this method in SizeService
      .then(() => {
        message.success("Xóa kích thước thành công");
        loadProductSizes(selectedColorId); // Refresh sizes for the selected color
      })
      .catch((error) => {
        message.error(`Lỗi khi xóa kích thước: ${error.response?.data?.message || error.message}`);
      });
  };

 
  const handleAddImage = () => {
    imageForm.validateFields()
      .then((values) => {
        console.log('Validated values:', values);
        const { imageFile } = values;
        const formData = new FormData();
        const selectedFiles = imageFile || [];

        if (Array.isArray(selectedFiles)) {
          selectedFiles.forEach((file) => {
            formData.append('imageFiles', file.originFileObj);
          });
        }

        formData.append('productColorId', selectedColorId);

        ProductColorImageService.addImageToColor(formData)
          .then(() => {
            message.success("Thêm hình ảnh thành công");
            loadProductColorImages(selectedColorId);
            imageForm.resetFields();
          })
          .catch((error) => {
            message.error(`Lỗi khi thêm hình ảnh: ${error.response?.data?.message || error.message}`);
          });
      })
      .catch((error) => {
        console.error('Validation Error:', error);
        message.error("Vui lòng nhập thông tin hợp lệ");
      });
  };
  
  

  const handleDeleteImage = (imageId) => {
    ProductColorImageService.deleteImageFromColor(imageId)
      .then(() => {
        message.success("Xóa hình ảnh thành công");
        loadProductColorImages(selectedColorId);
      })
      .catch((error) => {
        message.error(`Lỗi khi xóa hình ảnh: ${error.response?.data?.message || error.message}`);
      });
  };

  const colorColumns = [
    {
      title: 'Hình ảnh',
      dataIndex: 'imageUrl',
      key: 'imageUrl',
      render: (text) => (
        text ? <img src={text} alt="Màu sắc" style={{ width: 50, height: 50 }} /> : 'Không có ảnh'
      ),
    },
    {
      title: 'Màu sắc',
      dataIndex: 'colorName',
      key: 'colorName',
      render: (text, record) => (
        <Button type="link" onClick={() => handleColorSelect(record.productColorId)}>
          {text}
        </Button>
      ),
    },
    {
      title: 'Hành động',
      render: (text, record) => (
        <Button type="link" onClick={() => handleDeleteColor(record.productColorId)}>Xóa</Button>
      ),
    },
  ];

  const sizeColumns = [
    {
      title: 'Kích thước',
      dataIndex: 'sizeValue',
      key: 'sizeValue',
    },
    {
      title: 'Số lượng tồn kho',
      dataIndex: 'stockQuantity',
      key: 'stockQuantity',
    },
    {
      title: 'Hành động',
      render: (text, record) => (
        <Button type="link" onClick={() => handleDeleteSize(record.productSizeId)}>Xóa</Button> // Add delete functionality
      ),
    },
  ];

  const imageColumns = [
    {
      title: 'Hình ảnh',
      dataIndex: 'imageUrl',
      key: 'imageUrl',
      render: (text) => (
        <img src={text} alt="Hình ảnh sản phẩm" style={{ width: 50, height: 50 }} />
      ),
    },
    {
      title: 'Hành động',
      render: (text, record) => (
        <Button type="link" onClick={() => handleDeleteImage(record.productColorImageId)}>Xóa</Button>
      ),
    },
  ];

  return (
    <Modal
      title={`Quản lý màu và kích thước cho ${product ? product.productName : ''}`}
      open={!!product}
      onCancel={onCancel}
      footer={null}
    >
      <Form form={colorForm} layout="horizontal" style={{ marginBottom: '16px' }}>
        <Form.Item
          name="colorName"
          label="Tên màu"
          rules={[{ required: true, message: 'Vui lòng nhập tên màu!' }]}
        >
          <Input placeholder="Nhập tên màu" />
        </Form.Item>

        <Form.Item
          name="imageFile"
          label="Tải lên hình ảnh"
          valuePropName="fileList"
          getValueFromEvent={(e) => (Array.isArray(e) ? e : e && e.fileList)}
          rules={[{ required: true, message: 'Vui lòng tải lên hình ảnh!' }]}
        >
          <Upload
            listType="picture"
            beforeUpload={() => false}
            maxCount={1}
          >
            <Button icon={<UploadOutlined />}>Chọn hình ảnh</Button>
          </Upload>
        </Form.Item>

        <Form.Item>
          <Button type="primary" onClick={handleAddColor}>
            Thêm màu
          </Button>
        </Form.Item>
      </Form>

      <Table
        columns={colorColumns}
        dataSource={productColors}
        rowKey="productColorId"
        pagination={false}
        title={() => <h3>Màu sắc của sản phẩm</h3>}
      />

      <Form form={sizeForm} layout="horizontal" style={{ marginBottom: '16px' }}>
        <Form.Item
          name="sizeValue"
          label="Kích thước"
          rules={[{ required: true, message: 'Vui lòng nhập kích thước!' }]}
        >
          <Input placeholder="Nhập kích thước" />
        </Form.Item>
        <Form.Item
          name="stockQuantity"
          label="Số lượng tồn kho"
          rules={[{ required: true, message: 'Vui lòng nhập số lượng!' }]}
        >
          <Input type="number" placeholder="Nhập số lượng" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" onClick={handleAddSize} disabled={!selectedColorId}>
            Thêm kích thước
          </Button>
        </Form.Item>
      </Form>

      <Table
        columns={sizeColumns}
        dataSource={productSizes}
        rowKey="productSizeId"
        pagination={false}
        title={() => <h3>Kích thước cho màu đã chọn</h3>}
      />

    <Form form={imageForm} layout="horizontal" style={{ marginBottom: '16px' }}>
      <Form.Item
        name="imageFile"
        label="Hình ảnh màu sắc"
        valuePropName="fileList"
        getValueFromEvent={(e) => (Array.isArray(e) ? e : e && e.fileList)}
        rules={[{ required: true, message: 'Vui lòng tải lên hình ảnh!' }]} // This ensures that the field is required
      >
        <Upload
          listType="picture"
          beforeUpload={() => false}
          maxCount={10}
        >
          <Button icon={<UploadOutlined />}>Chọn hình ảnh</Button>
        </Upload>
      </Form.Item>

      <Form.Item>
        <Button type="primary" onClick={handleAddImage} disabled={!selectedColorId}>
          Thêm hình ảnh
        </Button>
      </Form.Item>
    </Form>


      <Table
        columns={imageColumns}
        dataSource={productColorImages}
        rowKey="productColorImageId"
        pagination={false}
        title={() => <h3>Hình ảnh cho màu đã chọn</h3>}
      />
    </Modal>
  );
};

export default ProductColorModal;
