package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.category.CategoryCreateResult;
import ir.maktab.firstspringboot.api.category.CategoryUpdateResult;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryCreateParam;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryListResult;
import ir.maktab.firstspringboot.api.category.main_category.MainCategoryUpdateParam;
import ir.maktab.firstspringboot.model.entity.category.MainCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainCategoryServiceTest {

    @Autowired
    private MainCategoryService mainCategoryService;

    @Test
    void saveMainCategory() {
        MainCategoryCreateParam createParam = new MainCategoryCreateParam("MainCategory-4");

        CategoryCreateResult categoryCreateResult = mainCategoryService.saveMainCategory(createParam);
        assertEquals(4L, categoryCreateResult.getId());
    }

    @Test
    void updateMainCategory() {
        MainCategoryUpdateParam updateParam = new MainCategoryUpdateParam(4L, "MainCategory-4-Updated");

        CategoryUpdateResult categoryUpdateResult = mainCategoryService.updateMainCategory(updateParam);
        assertTrue(categoryUpdateResult.isSuccess());
    }

    @Test
    @Transactional(readOnly = true)
    void loadById() {
        MainCategory mainCategory = mainCategoryService.loadById(5);
        mainCategory.getSubCategorySet().forEach((s)-> System.out.println(s.getId()));
        System.out.println("mainCategory.getName() = " + mainCategory.getName());
        assertNotNull(mainCategory);
    }

    @Test
    void loadAll() {
        MainCategoryListResult mainCategoryListResult = mainCategoryService.loadAll();
        assertEquals(4, mainCategoryListResult.getMainCategoryModels().size());
    }
}

