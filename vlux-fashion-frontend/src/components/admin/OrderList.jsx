// src/components/admin/OrderList.js

import React, { useEffect, useState } from 'react';
import OrderService from '../../services/admin/OrderService';
import { Table, message } from 'antd';

const OrderList = () => {
  const [orders, setOrders] = useState([]);

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
  ];

  return (
    <div>
      <Table 
        dataSource={orders} 
        columns={columns} 
        rowKey="orderId" 
        pagination={{ pageSize: 8 }} 
      />
    </div>
  );
};

export default OrderList;
