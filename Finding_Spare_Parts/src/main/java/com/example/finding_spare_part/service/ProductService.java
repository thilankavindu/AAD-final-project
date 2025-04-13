package com.example.finding_spare_part.service;
//
//import com.example.finding_spare_part.dto.ProductDTO;
//
//import java.util.List;
//
//public interface ProductService {
//    List<ProductDTO> getAllProducts();
//    ProductDTO getProductById(Long id);
//    ProductDTO addProduct(ProductDTO productDTO);
//    ProductDTO updateProduct(Long id, ProductDTO productDTO);
//    void deleteProduct(Long id);
//}


import com.example.finding_spare_part.dto.ProductDTO;
import com.example.finding_spare_part.entity.Product;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(Long id);
    List<Product> getAllProducts();
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}