package ir.maktab.firstspringboot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import ir.maktab.firstspringboot.api.category.CategoryCreateResult;
import ir.maktab.firstspringboot.api.category.sub_category.AddProficientToSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.RemoveProficientFromSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.SubCategoryCreateParam;
import ir.maktab.firstspringboot.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subCategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    @Operation(summary = "Create new subCategory")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryCreateResult> createSubCategory(@RequestBody SubCategoryCreateParam createParam) {
        CategoryCreateResult categoryCreateResult = subCategoryService.saveSubCategory(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreateResult);
    }

    @PutMapping("/{subCategoryId}/proficients/{proficientId}")
    @PreAuthorize("hasAuthority('sub_category:write')")
    public ResponseEntity<AddProficientToSubCatResult> addProficientToSubCat(
            @PathVariable long subCategoryId, @PathVariable long proficientId) {
        AddProficientToSubCatResult addProficientToSubCatResult = subCategoryService.addProficient(proficientId, subCategoryId);
        return ResponseEntity.ok(addProficientToSubCatResult);
    }

    @DeleteMapping("/{subCategoryId}/proficients/{proficientId}")
    @PreAuthorize("hasAuthority('sub_category:write')")
    public ResponseEntity<RemoveProficientFromSubCatResult> removeProficientFromSubCat(
            @PathVariable long subCategoryId, @PathVariable long proficientId) {
        RemoveProficientFromSubCatResult removeProficientFromSubCatResult = subCategoryService.removeProficient(proficientId, subCategoryId);
        return ResponseEntity.ok(removeProficientFromSubCatResult);
    }

}

