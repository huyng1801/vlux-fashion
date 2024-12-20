import React, { useEffect, useState } from 'react';
import OrderService from '../../services/admin/OrderService';
import { Table, message, Button, Modal, Descriptions, List } from 'antd';
import { EyeOutlined } from '@ant-design/icons'; // Import the icon

const OrderList = () => {
  const [orders, setOrders] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [orderItems, setOrderItems] = useState([]); // To hold the order items

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = () => {
    OrderService.getAllOrders()
      .then((response) => {
        setOrders(response);
      })
      .catch(() => {
        message.error("Lỗi khi tải đơn hàng");
      });
  };

  // Define the columns based on OrderResponse properties
  const columns = [
    {
      title: 'ID',
      dataIndex: 'orderId',
      key: 'orderId',
    },
    {
      title: 'Tổng giá',
      dataIndex: 'totalPrice',
      key: 'totalPrice',
      render: (totalPrice) => `${totalPrice.toLocaleString()} VND`, // Format as currency
    },
    {
      title: 'Tên khách (khách mời)',
      dataIndex: 'guestName',
      key: 'guestName',
    },
    {
      title: 'Hành động',
      key: 'action',
      render: (text, record) => (
        <Button 
          type="link" // Outline style
          icon={<EyeOutlined />} // Add the icon
          onClick={() => handleViewDetails(record)}
        />
      ),
    },
  ];

  const handleViewDetails = (order) => {
    setSelectedOrder(order);
    // Fetch the order items by orderId
    OrderService.getOrderItemsByOrderId(order.orderId)
      .then((items) => {
        setOrderItems(items);
      })
      .catch(() => {
        message.error("Lỗi khi tải chi tiết đơn hàng");
      });
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setSelectedOrder(null);
    setOrderItems([]); // Reset order items when closing modal
  };

  return (
    <div>
      <Table 
        dataSource={orders} 
        columns={columns} 
        rowKey="orderId" 
        pagination={{ pageSize: 8 }} 
      />

      <Modal
        title={`Chi tiết đơn hàng: ${selectedOrder?.orderId}`}
        visible={isModalVisible}
        onCancel={handleCancel}
        footer={null}
        width={800}
      >
        {selectedOrder && (
          <>
            <Descriptions bordered column={2}>
              <Descriptions.Item label="ID đơn hàng">{selectedOrder.orderId}</Descriptions.Item>
              <Descriptions.Item label="Ghi chú">{selectedOrder.orderNote || 'Không có'}</Descriptions.Item>
              <Descriptions.Item label="Phương thức thanh toán">{selectedOrder.paymentMethod}</Descriptions.Item>
              <Descriptions.Item label="Tổng giá">{`${selectedOrder.totalPrice.toLocaleString()} VND`}</Descriptions.Item>
              <Descriptions.Item label="Trạng thái đơn hàng">{selectedOrder.orderStatus}</Descriptions.Item>
              <Descriptions.Item label="Trạng thái thanh toán">{selectedOrder.isPaid ? 'Đã thanh toán' : 'Chưa thanh toán'}</Descriptions.Item>
              <Descriptions.Item label="Tên khách">{selectedOrder.guestName || 'Khách hàng đăng ký'}</Descriptions.Item>
            </Descriptions>

            <h3>Danh sách sản phẩm trong đơn hàng</h3>
            <List
              itemLayout="horizontal"
              dataSource={orderItems}
              renderItem={(item) => (
                <List.Item>
                  <List.Item.Meta
                    title={`${item.productName} (${item.colorName}, ${item.sizeValue})`}
                    description={`Số lượng: ${item.quantity} - Giá: ${item.unitPrice.toLocaleString()} VND`}
                  />
                </List.Item>
              )}
            />
          </>
        )}
      </Modal>
    </div>
  );
};

export default OrderList;
