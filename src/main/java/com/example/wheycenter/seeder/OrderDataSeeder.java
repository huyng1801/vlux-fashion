package com.example.wheycenter.seeder;

import com.example.wheycenter.model.Order;
import com.example.wheycenter.model.PaymentMethod;
import com.example.wheycenter.repository.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderDataSeeder {

    private static final Logger logger = LoggerFactory.getLogger(OrderDataSeeder.class);

    @Bean
    CommandLineRunner seedOrders(OrderRepository orderRepository) {
        return args -> {
            if (orderRepository.count() == 0) {
                // Create and save orders
                Order order1 = new Order();
                order1.setOrderId("20240904145050");
                order1.setCustomerFirstName("Nguyễn");
                order1.setCustomerLastName("Văn A");
                order1.setCompanyName("Công ty TNHH ABC");
                order1.setCountry("Việt Nam");
                order1.setAddress("123 Đường Lê Lợi");
                order1.setAddress2("Tầng 3");
                order1.setPostalCode("700000");
                order1.setCity("TP. Hồ Chí Minh");
                order1.setPhone("090-123-4567");
                order1.setEmail("nguyen.vana@example.com");
                order1.setOrderNote("Vui lòng giao hàng trước 17h.");
                order1.setPaymentMethod(PaymentMethod.CREDIT_CARD);
                order1.setTotalPrice(BigDecimal.valueOf(499000));
                order1.setIsPaid(true);
                order1.setIsDelivered(false);
                // Set additional fields if necessary
                orderRepository.save(order1);

                Order order2 = new Order();
                order2.setOrderId("20240904145150");
                order2.setCustomerFirstName("Trần");
                order2.setCustomerLastName("Thị B");
                order2.setCompanyName(null);
                order2.setCountry("Việt Nam");
                order2.setAddress("456 Đường Nguyễn Huệ");
                order2.setAddress2(null);
                order2.setPostalCode("100000");
                order2.setCity("Hà Nội");
                order2.setPhone("091-234-5678");
                order2.setEmail("tran.thib@example.com");
                order2.setOrderNote("Để lại trước cửa nhà.");
                order2.setPaymentMethod(PaymentMethod.PAYPAL);
                order2.setTotalPrice(BigDecimal.valueOf(299000));
                order2.setIsPaid(false);
                order2.setIsDelivered(false);
                // Set additional fields if necessary
                orderRepository.save(order2);

                logger.info("Dữ liệu đơn hàng đã được thêm vào thành công!");
            }
        };
    }
}
