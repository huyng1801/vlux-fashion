package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.student.vluxfashion.dto.GuestDto;
import vn.student.vluxfashion.dto.OrderDto;
import vn.student.vluxfashion.dto.OrderItemDto;
import vn.student.vluxfashion.dto.OrderRequestDto;
import vn.student.vluxfashion.model.Banner;
import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.model.Guest;
import vn.student.vluxfashion.model.Order;
import vn.student.vluxfashion.response.CategoryResponse;
import vn.student.vluxfashion.response.OrderResponse;
import vn.student.vluxfashion.response.ProductColorImageResponse;
import vn.student.vluxfashion.response.ProductColorResponse;
import vn.student.vluxfashion.response.ProductResponse;
import vn.student.vluxfashion.response.ProductSizeResponse;
import vn.student.vluxfashion.response.SubCategoryResponse;
import vn.student.vluxfashion.service.BannerService;
import vn.student.vluxfashion.service.CategoryService;
import vn.student.vluxfashion.service.OrderService;
import vn.student.vluxfashion.service.ProductColorImageService;
import vn.student.vluxfashion.service.ProductColorService;
import vn.student.vluxfashion.service.ProductService;
import vn.student.vluxfashion.service.ProductSizeService;
import vn.student.vluxfashion.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/home/")
public class HomeController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ProductColorService productColorService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private ProductColorImageService productColorImageService;

 @Autowired
    private OrderService orderService; 


    @GetMapping("/product-color/{productId}")
    public ResponseEntity<List<ProductColorResponse>> findByProductId(@PathVariable Integer productId) {
        List<ProductColorResponse> response = productColorService.findByProduct_ProductId(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("banners")
    public ResponseEntity<List<Banner>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }
    // Endpoint to get all categories
    @GetMapping("categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    // Endpoint to get subcategories based on categoryId and gender
    @GetMapping("subcategories")
    public ResponseEntity<List<SubCategoryResponse>> getSubCategories(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "gender", required = false) Gender gender) {
        
        List<SubCategoryResponse> subCategories = subCategoryService.getSubCategories(categoryId, gender);
        return ResponseEntity.ok(subCategories);
    }

    // Endpoint to get all products based on various filtering parameters
    @GetMapping("products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) Integer subCategoryId,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) String productName) {
        
        List<ProductResponse> productResponses = productService.getAllProducts(subCategoryId, gender, productName);
        return ResponseEntity.ok(productResponses);
    }
    @GetMapping("product-size/product-color/{productColorId}")
    public ResponseEntity<List<ProductSizeResponse>> findByProductColorId(@PathVariable Integer productColorId) {
        List<ProductSizeResponse> response = productSizeService.findByProductColorId(productColorId);
        return ResponseEntity.ok(response);
    }
      // Get all images by Product Color ID
    @GetMapping("product-image/product-color/{productColorId}")
    public ResponseEntity<List<ProductColorImageResponse>> getImagesByProductColorId(@PathVariable Integer productColorId) {
        List<ProductColorImageResponse> productColorImages = productColorImageService.findByProductColorId(productColorId);
        return ResponseEntity.ok(productColorImages);
    }
    // Get product by ID
    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Integer productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        if (productResponse == null) {
            return ResponseEntity.notFound().build(); // Handle not found case
        }
        return ResponseEntity.ok(productResponse);
    }
    @PostMapping("orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        // Extract guest and order details from the request DTO
        GuestDto guestDto = orderRequestDto.getGuestDto();
        OrderDto orderDto = orderRequestDto.getOrderDto();
        List<OrderItemDto> orderItemDtos = orderRequestDto.getOrderItemDtos();
    
        // Call the order service to create the order with the provided guest and order items
        OrderResponse orderResponse = orderService.createOrder(guestDto, orderDto, orderItemDtos);
        return ResponseEntity.ok(orderResponse);
    }
    

}
