package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.repository.CategoryRepository;
import com.example.backendsaleswebsite.dto.ProductDTO;
import com.example.backendsaleswebsite.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.backendsaleswebsite.dto.ProductResponseDTO;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;


    // Phương thức để lấy danh sách tất cả sản phẩm
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDTOResponse)
                .collect(Collectors.toList());
    }
    // Phân trang cho product

    public Page<Product> getProductsByCategory(String categoryName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByCategoryName(categoryName, pageable);
    }

    // Thêm sản phẩm mới

    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + productDTO.getCategoryId()));
        Product product = toEntity(productDTO, category);
        Product savedProduct = productRepository.save(product);
        return toDTO(savedProduct);
    }


 // Lấy sản phẩm theo ID và trả về ProductResponseDTO
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        return toDTOResponse(product); // Chuyển Product thành ProductResponseDTO
    }


    // Xóa sản phẩm theo ID
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // Cập nhật thông tin sản phẩm
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + productDTO.getCategoryId()));

        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setManufacturer(productDTO.getManufacturer());
        existingProduct.setProductDescription(productDTO.getProductDescription());
        existingProduct.setCost(productDTO.getCost());
        existingProduct.setProductImage(productDTO.getProductImage());
        existingProduct.setProductQuantity(productDTO.getProductQuantity());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        return toDTO(updatedProduct);
    }
    
 // Chuyển đổi từ Product sang ProductDTO
    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getManufacturer(),
                product.getProductDescription(),
                product.getCost(),
                product.getCategory().getCategoryId(),
                product.getProductImage(),
                product.getProductQuantity()
     
        );
    }
    
 // Chuyển đổi từ ProductDTO sang Product
    private Product toEntity(ProductDTO dto, Category category) {
        return new Product(
                dto.getProductId(),
                dto.getProductName(),
                dto.getManufacturer(),
                dto.getProductDescription(),
                dto.getCost(),
                dto.getProductImage(),
                dto.getProductQuantity(),
                category
               
        );
    }

    private ProductResponseDTO toDTOResponse(Product product) {
        return new ProductResponseDTO(
                product.getProductId(),
                product.getProductName(),
                product.getManufacturer(),
                product.getProductDescription(),
                product.getCost(),
                product.getProductImage(),
                product.getProductQuantity(),
                product.getCategory().getCategoryName(),
                product.getCategory().getCategoryId()
        );
    }
    
    public Page<ProductResponseDTO> searchProductsByName(String productName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByProductNameContainingIgnoreCase(productName, pageable)
                .map(this::toDTOResponse);
    }

    // Tìm kiếm sản phẩm theo hãng
    public Page<ProductResponseDTO> searchProductsByManufacturer(String manufacturer, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByManufacturerContainingIgnoreCase(manufacturer, pageable)
                .map(this::toDTOResponse);
    }
    
    
}
