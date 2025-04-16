package com.example.finding_spare_part.controller;

import com.example.finding_spare_part.dto.CategoryDTO;
import com.example.finding_spare_part.dto.ResponseDTO;
import com.example.finding_spare_part.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/save")
    public CategoryDTO createCategory(
            @RequestParam("id") long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("imageUrl") MultipartFile image) throws IOException {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);

        if (!image.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/category/";

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path filePath = Paths.get(uploadDir + filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            categoryDTO.setImageUrl(filename);
        }

        return categoryService.createCategory(categoryDTO);
    }

    @PutMapping("/update/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.updateCategory(id, categoryDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(VarList.OK, "Success", categoryService.getAllCategories()));
    }
}