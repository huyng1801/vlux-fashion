package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.model.Product;
import vn.student.vluxfashion.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, Product product) {
        if (productRepository.existsById(productId)) {
            product.setProductId(productId);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
