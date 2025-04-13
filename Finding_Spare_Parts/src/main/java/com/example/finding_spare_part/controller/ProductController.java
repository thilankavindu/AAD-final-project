package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.CategoryDTO;
import com.example.finding_spare_part.dto.ProductDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.entity.Product;
import com.example.finding_spare_part.service.ProductService;
import com.example.finding_spare_part.service.ProductService;
import com.example.finding_spare_part.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
@RequestMapping("/api/vi/product")
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
    @PostMapping("save")
    public ProductDTO createProduct(@RequestParam ("id") long id,
                                      @RequestParam ("name") String name,
                                      @RequestParam ("description") String description,
                                      @RequestParam ("price") double price,
                                      @RequestParam ("stockQuantity") int stockQuantity,
                                      @RequestParam ("imageUrl") MultipartFile image,
                                      @RequestParam ("categoryName") String categoryName,
                                      @RequestParam ("categoryId") long categoryId
    ) throws IOException {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setStockQuantity(stockQuantity);
        productDTO.setImageUrl(String.valueOf(image));
        productDTO.setCategoryName(categoryName);
        productDTO.setCategoryId(categoryId);

        try {
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

                System.out.println(productDTO.getImageUrl() + "fghjki   ");

                //categoryService.createCategory(categoryDTO);// Set updated list
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return productService.createProduct(productDTO);
    }
    @PutMapping("update")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }
    @DeleteMapping("delete")
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




//    @Autowired
//    private ProductService productService;
//
//    @PostMapping("save")
//    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
//        return ResponseEntity.ok(productService.createProduct(productDTO));
//    }
////    @PostMapping("save")
////    public String createProduct() {
////return "save method";    }
//
//    @GetMapping("/lkl")
//    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<List<Product>> getAllProducts() {
////        return ResponseEntity.ok(productService.getAllProducts());
//        return ResponseEntity.ok(productService.getAllProducts());
//    }
//
//    @GetMapping("/test")
//    public String test() {
////        return ResponseEntity.ok(productService.getAllProducts());
//        return "test  111111";
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
//        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.ok("Product deleted successfully");
//    }

