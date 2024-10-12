package vn.student.vluxfashion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import vn.student.vluxfashion.dto.SubCategoryDto;
import vn.student.vluxfashion.model.Gender;
import vn.student.vluxfashion.response.SubCategoryResponse;
import vn.student.vluxfashion.service.SubCategoryService;
import vn.student.vluxfashion.util.ValidationUtils;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<List<SubCategoryResponse>> getSubCategories(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "gender", required = false) Gender gender) { // Gender parameter as an enum
        List<SubCategoryResponse> subCategories;

    
            subCategories = subCategoryService.getSubCategories(categoryId, gender);

        return ResponseEntity.ok(subCategories);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> getSubCategoryById(@PathVariable Integer id) {
        SubCategoryResponse subCategory = subCategoryService.getSubCategoryById(id);
        return ResponseEntity.ok(subCategory);
    }



    @PostMapping
    public ResponseEntity<?> createSubCategory(@Valid @RequestBody SubCategoryDto subCategoryDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }
        SubCategoryResponse subCategory = subCategoryService.createSubCategory(subCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubCategory(@PathVariable Integer id, @Valid @RequestBody SubCategoryDto subCategoryDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationUtils.getErrorMessages(result));
        }
        SubCategoryResponse subCategory = subCategoryService.updateSubCategory(id, subCategoryDto);
        return ResponseEntity.ok(subCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Integer id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}
