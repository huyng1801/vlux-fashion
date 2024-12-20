package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.model.*;
import vn.student.vluxfashion.repository.*;

import java.util.*;

@Service
public class StatisticService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Get today's revenue and item statistics
    public Map<String, Object> getStatisticsToday() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
        int day = today.get(Calendar.DAY_OF_MONTH);

        List<Order> orders = orderRepository.findOrdersByDate(year, month, day);
        Map<String, Object> statistics = calculateStatistics(orders);

        statistics.put("totalBanners", bannerRepository.count());
        statistics.put("totalBrands", brandRepository.count());
        statistics.put("totalCategories", categoryRepository.count());
        statistics.put("totalCustomers", customerRepository.count());
        statistics.put("totalProducts", productRepository.count());
        statistics.put("totalOrders", orderRepository.countOrdersByDate(year, month, day));

        // Add best-selling products
        statistics.put("bestSellingProducts", getBestSellingProducts());

        return statistics;
    }

    // Get current month's revenue and item statistics
    public Map<String, Object> getStatisticsThisMonth() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;

        List<Order> orders = orderRepository.findOrdersByMonth(year, month);
        Map<String, Object> statistics = calculateStatistics(orders);

        statistics.put("totalBanners", bannerRepository.count());
        statistics.put("totalBrands", brandRepository.count());
        statistics.put("totalCategories", categoryRepository.count());
        statistics.put("totalCustomers", customerRepository.count());
        statistics.put("totalProducts", productRepository.count());
        statistics.put("totalOrders", orderRepository.countOrdersByMonth(year, month));

        // Add best-selling products
        statistics.put("bestSellingProducts", getBestSellingProducts());

        return statistics;
    }

    // Get current year's revenue and item statistics
    public Map<String, Object> getStatisticsThisYear() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);

        List<Order> orders = orderRepository.findOrdersByYear(year);
        Map<String, Object> statistics = calculateStatistics(orders);

        statistics.put("totalBanners", bannerRepository.count());
        statistics.put("totalBrands", brandRepository.count());
        statistics.put("totalCategories", categoryRepository.count());
        statistics.put("totalCustomers", customerRepository.count());
        statistics.put("totalProducts", productRepository.count());
        statistics.put("totalOrders", orderRepository.countOrdersByYear(year));

        // Add best-selling products
        statistics.put("bestSellingProducts", getBestSellingProducts());

        return statistics;
    }

    // Get item quantity by category
    public Map<String, Integer> getItemQuantityByCategory() {
        Map<String, Integer> categoryQuantities = new HashMap<>();
        List<Order> orders = orderRepository.findOrdersByYear(Calendar.getInstance().get(Calendar.YEAR)); // Get orders for the current year

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            for (OrderItem item : orderItems) {
                String categoryName = item.getProductSize().getProductColor().getProduct().getSubCategory().getCategory().getCategoryName();
                categoryQuantities.put(categoryName, categoryQuantities.getOrDefault(categoryName, 0) + item.getQuantity());
            }
        }

        return categoryQuantities;
    }

    // Calculate revenue and quantity from orders
    private Map<String, Object> calculateStatistics(List<Order> orders) {
        Map<String, Object> statistics = new HashMap<>();
        double totalRevenue = 0;
        int totalQuantity = 0;

        // Loop through all orders and order items to calculate revenue and quantity
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
            for (OrderItem item : orderItems) {
                totalRevenue += item.getUnitPrice() * item.getQuantity(); // Assuming price and quantity are available
                totalQuantity += item.getQuantity();
            }
        }

        statistics.put("totalRevenue", totalRevenue);
        statistics.put("totalQuantity", totalQuantity);
        return statistics;
    }

    // Get best-selling products (most sold)
    private List<Map<String, Object>> getBestSellingProducts() {
        List<Map<String, Object>> bestSellingProducts = new ArrayList<>();
        List<Object[]> results = orderItemRepository.findBestSellingProducts(); // Assuming custom query to get top-selling products

        for (Object[] result : results) {
            Map<String, Object> productStats = new HashMap<>();
            productStats.put("productName", result[0]); // Assuming result[0] is product name
            productStats.put("totalQuantitySold", result[1]); // Assuming result[1] is total quantity sold
            bestSellingProducts.add(productStats);
        }

        return bestSellingProducts;
    }

    // Get monthly statistics for the current year (for charting)
    public List<Map<String, Object>> getMonthlyStatisticsForCurrentYear() {
        int year = Calendar.getInstance().get(Calendar.YEAR); // Get the current year
        List<Map<String, Object>> monthlyStats = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Map<String, Object> stats = getStatisticsByMonth(year, month);  // Reuse existing method to get monthly stats
            monthlyStats.add(stats);
        }

        return monthlyStats;
    }

    // Get statistics by month (reused from previous implementation)
    private Map<String, Object> getStatisticsByMonth(int year, int month) {
        List<Order> orders = orderRepository.findOrdersByMonth(year, month);
        return calculateStatistics(orders);
    }

    // Get daily statistics for a specific month (for charting)
    public List<Map<String, Object>> getDailyStatisticsForMonth(int year, int month) {
        // Generate statistics for each day in the month
        List<Map<String, Object>> dailyStats = new ArrayList<>();
        int daysInMonth = java.time.Month.of(month).length(java.time.Year.isLeap(year)); // Get the number of days in the month
        for (int day = 1; day <= daysInMonth; day++) {
            Map<String, Object> stats = getStatisticsByDay(year, month, day);
            dailyStats.add(stats);
        }
        return dailyStats;
    }
    public Map<String, Object> getStatisticsByDay(int year, int month, int day) {
        // Retrieve orders for the specific day using the repository
        List<Order> orders = orderRepository.findOrdersByDate(year, month, day);
        
        // Calculate statistics for the retrieved orders
        return calculateStatistics(orders);
    }
    
}
