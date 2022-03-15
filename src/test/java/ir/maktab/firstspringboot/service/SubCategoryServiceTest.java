package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.category.CategoryCreateResult;
import ir.maktab.firstspringboot.api.category.sub_category.AddProficientToSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.RemoveProficientFromSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.SubCategoryCreateParam;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubCategoryServiceTest {

    @Autowired
    private SubCategoryService subCategoryService;

    @Test
    void test_add_proficient_isOk() {
        AddProficientToSubCatResult addProficientToSubCatResult = subCategoryService.addProficient(9, 2);
        assertTrue(addProficientToSubCatResult.isSuccess());
    }

    @Test
    void test_remove_proficient_isOk() {
        RemoveProficientFromSubCatResult removeProficientFromSubCatResult =
                subCategoryService.removeProficient(9, 2);
        assertTrue(removeProficientFromSubCatResult.isSuccess());
    }

    @Test
    void test_load_by_mainCategory_id() {
        List<SubCategory> subCategories = subCategoryService.loadByMainCategoryId(1);
        subCategories.forEach(System.out::println);
        assertEquals(4, subCategories.size());
    }

    @Test
    void saveSubCategory() {
        SubCategoryCreateParam createParam = new SubCategoryCreateParam("Sub-Cat-7", 3);
        CategoryCreateResult categoryCreateResult = subCategoryService.saveSubCategory(createParam);
        assertEquals(7L, categoryCreateResult.getId());
    }
}

