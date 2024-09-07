package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Order;
import com.example.wheycenter.model.OrderItem;
import com.example.wheycenter.model.ProductFlavor;
import com.example.wheycenter.repository.OrderItemRepository;
import com.example.wheycenter.repository.OrderRepository;
import com.example.wheycenter.repository.ProductFlavorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemDataSeeder.class);

    @Bean
    CommandLineRunner seedOrderItems(
            OrderItemRepository orderItemRepository,
            OrderRepository orderRepository,
            ProductFlavorRepository productFlavorRepository) {
        return args -> {
            if (orderItemRepository.count() == 0) {
                // Fetch existing orders and product flavors
                Order order1 = orderRepository.findById("20240904145050").orElseThrow();
                Order order2 = orderRepository.findById("20240904145150").orElseThrow();

                ProductFlavor flavor1 = productFlavorRepository.findById(1).orElseThrow();
                ProductFlavor flavor2 = productFlavorRepository.findById(2).orElseThrow();

                // Create and save order items
                OrderItem item1 = new OrderItem();
                item1.setQuantity(2);
                item1.setUnitPrice(BigDecimal.valueOf(2200000)); 
                item1.setOrder(order1);
                item1.setProductFlavor(flavor1);
                orderItemRepository.save(item1);

                OrderItem item2 = new OrderItem();
                item2.setQuantity(1);
                item2.setUnitPrice(BigDecimal.valueOf(1300000)); 
                item2.setOrder(order1);
                item2.setProductFlavor(flavor2);
                orderItemRepository.save(item2);

                OrderItem item3 = new OrderItem();
                item3.setQuantity(3);
                item3.setUnitPrice(BigDecimal.valueOf(2200000)); 
                item3.setOrder(order2);
                item3.setProductFlavor(flavor1);
                orderItemRepository.save(item3);

                logger.info("Dữ liệu mục đơn hàng đã được thêm vào thành công!");
            }
        };
    }
}
