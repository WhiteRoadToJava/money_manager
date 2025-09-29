package dev.mohammad.mymonymanager.controller;

import dev.mohammad.mymonymanager.dbo.CategoryDTO;
import dev.mohammad.mymonymanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO saveCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categorise = categoryService.getCategoriesForCurrentUser();
        return ResponseEntity.ok(categorise);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesForCurrentUser(@PathVariable String type) {
        List<CategoryDTO> categorise = categoryService.categoriesByTypeForCurrentUser(type);
        return ResponseEntity.ok(categorise);
    }

    @PutMapping("/{categoryid}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryid, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryid, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }
}
