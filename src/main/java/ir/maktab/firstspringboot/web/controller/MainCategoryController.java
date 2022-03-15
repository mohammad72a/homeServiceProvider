package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.category.CategoryCreateResult;
import ir.maktab.firstspringboot.api.category.CategoryUpdateResult;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryCreateParam;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryListResult;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryModel;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryUpdateParam;
import ir.maktab.firstspringboot.service.MainCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mainCategories")
public class MainCategoryController {

    private final MainCategoryService mainCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryCreateResult> createMainCategory(@RequestBody MainCategoryCreateParam createParam) {
        CategoryCreateResult categoryCreateResult = mainCategoryService.saveMainCategory(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreateResult);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryUpdateResult> updateMainCategory(@RequestBody MainCategoryUpdateParam updateParam, @PathVariable Long id) {
        updateParam.setId(id);
        CategoryUpdateResult categoryUpdateResult = mainCategoryService.updateMainCategory(updateParam);
        return ResponseEntity.ok(categoryUpdateResult);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('main_category:read')")
    public ResponseEntity<MainCategoryListResult> loadAll() {
        MainCategoryListResult mainCategoryListResult = mainCategoryService.loadAll();
        return ResponseEntity.ok(mainCategoryListResult);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('main_category:read')")
    public ResponseEntity<MainCategoryModel> loadById(@PathVariable Long id) {
        MainCategoryModel mainCategoryModel = mainCategoryService.loadByIdReturnModel(id);
        return ResponseEntity.ok(mainCategoryModel);
    }
}

