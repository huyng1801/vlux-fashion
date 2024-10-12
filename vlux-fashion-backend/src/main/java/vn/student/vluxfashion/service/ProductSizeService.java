package vn.student.vluxfashion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.student.vluxfashion.dto.ProductSizeDto;
import vn.student.vluxfashion.exception.ResourceNotFoundException;
import vn.student.vluxfashion.model.ProductColor;
import vn.student.vluxfashion.model.ProductSize;
import vn.student.vluxfashion.repository.ProductColorRepository;
import vn.student.vluxfashion.repository.ProductSizeRepository;
import vn.student.vluxfashion.response.ProductSizeResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSizeService {

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    public List<ProductSizeResponse> findAll() {
        return productSizeRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public ProductSizeResponse createProductSize(ProductSizeDto productSizeDto) {
        ProductColor productColor = productColorRepository.findById(productSizeDto.getProductColorId())
            .orElseThrow(() -> new ResourceNotFoundException("Product color not found with ID: " + productSizeDto.getProductColorId()));

        ProductSize productSize = new ProductSize();
        productSize.setSizeValue(productSizeDto.getSizeValue());
        productSize.setStockQuantity(productSizeDto.getStockQuantity());
        productSize.setProductColor(productColor);

        productSize = productSizeRepository.save(productSize);

        return mapToResponse(productSize);
    }

    public ProductSizeResponse updateProductSize(Integer id, ProductSizeDto productSizeDto) {
        ProductSize productSize = productSizeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product size not found with ID: " + id));

        ProductColor productColor = productColorRepository.findById(productSizeDto.getProductColorId())
            .orElseThrow(() -> new ResourceNotFoundException("Product color not found with ID: " + productSizeDto.getProductColorId()));

        productSize.setSizeValue(productSizeDto.getSizeValue());
        productSize.setStockQuantity(productSizeDto.getStockQuantity());
        productSize.setProductColor(productColor);

        productSize = productSizeRepository.save(productSize);

        return mapToResponse(productSize);
    }

    public void deleteProductSize(Integer id) {
        ProductSize productSize = productSizeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product size not found with ID: " + id));
        productSizeRepository.delete(productSize);
    }

    public ProductSizeResponse findById(Integer id) {
        ProductSize productSize = productSizeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product size not found with ID: " + id));
        return mapToResponse(productSize);
    }

    public List<ProductSizeResponse> findByProductColorId(Integer productColorId) {
        return productSizeRepository.findByProductColor_ProductColorId(productColorId)
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private ProductSizeResponse mapToResponse(ProductSize productSize) {
        return new ProductSizeResponse(
            productSize.getProductSizeId(),
            productSize.getSizeValue(),
            productSize.getStockQuantity(),
            productSize.getProductColor().getColorName()
        );
    }
}
