package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.ProductDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.entity.Product;
import com.example.finding_spare_part.service.ProductService;
import com.example.finding_spare_part.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/save")
    public ProductDTO createProduct(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("imageUrl") MultipartFile image,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("categoryId") long categoryId
    ) throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setCategoryName(categoryName);
        productDTO.setCategoryId(categoryId);

        if (!image.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/product/";

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path path = Paths.get(uploadDir + filename);
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            productDTO.setImageUrl(filename);
        }

        return productService.createProduct(productDTO);
    }

    @PutMapping("/update/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", productService.getAllProducts()));
    }
}