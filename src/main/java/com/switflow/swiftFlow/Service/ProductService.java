package com.switflow.swiftFlow.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.switflow.swiftFlow.Entity.Product;
import com.switflow.swiftFlow.Repo.ProductRepository;
import com.switflow.swiftFlow.Request.ProductRequest;
import com.switflow.swiftFlow.Response.ProductResponse;
import com.switflow.swiftFlow.Exception.ProductAlreadyExistsException;

@Service
public class ProductService {
    

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        String productCode = productRequest.getProductCode();
        
        if (productRepository.findByProductCode(productCode).isPresent()) {
            throw new ProductAlreadyExistsException("Product with code " + productCode + " already exists");
        }
        
        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName(productRequest.getProductName());
        product.setStatus("Active");
        
        // Set current date with dd-MM-yyyy format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        product.setDateAdded(LocalDate.now().format(formatter));

        
        Product savedProduct = productRepository.save(product);
        
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setProductCode(savedProduct.getProductCode());
        response.setProductName(savedProduct.getProductName());
        response.setStatus(savedProduct.getStatus());
        response.setDateAdded(savedProduct.getDateAdded());
        
        return response;
    }


    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setProductCode(product.getProductCode());
            response.setProductName(product.getProductName());
            response.setStatus(product.getStatus());
            response.setDateAdded(product.getDateAdded());
            return response;
        }).toList();
    }


    public ProductResponse getProductById(int id) {
        return productRepository.findById(id).map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setProductCode(product.getProductCode());
            response.setProductName(product.getProductName());
            response.setStatus(product.getStatus());
            response.setDateAdded(product.getDateAdded());
            return response;
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }


    public ProductResponse updateProduct(int id, ProductRequest productRequest) {
        return productRepository.findById(id).map(product -> {
            product.setProductName(productRequest.getProductName());
            product.setProductCode(productRequest.getProductCode());
            product.setStatus(productRequest.getStatus());
            
            // Set current date with dd-MM-yyyy format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            product.setDateAdded(LocalDate.now().format(formatter));
            
            Product updatedProduct = productRepository.save(product);
            return getProductById(updatedProduct.getId());
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProduct(int id) {
        productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return product;
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    
}