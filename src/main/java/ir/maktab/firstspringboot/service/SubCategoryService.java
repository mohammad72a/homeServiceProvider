package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.category.CategoryCreateResult;
import ir.maktab.firstspringboot.api.category.sub_category.AddProficientToSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.RemoveProficientFromSubCatResult;
import ir.maktab.firstspringboot.api.category.sub_category.SubCategoryCreateParam;
import ir.maktab.firstspringboot.model.entity.category.MainCategory;
import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryService mainCategoryService;
    private final ProficientService proficientService;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, MainCategoryService mainCategoryService, ProficientService proficientService) {
        this.subCategoryRepository = subCategoryRepository;
        this.mainCategoryService = mainCategoryService;
        this.proficientService = proficientService;
    }

    @Transactional
    public CategoryCreateResult saveSubCategory(SubCategoryCreateParam createParam) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(createParam.getName());

        MainCategory mainCategory = mainCategoryService.loadById(createParam.getMainCategoryId());
        subCategory.setMainCategory(mainCategory);
        mainCategory.addSubCategory(subCategory);

        SubCategory saveResult = subCategoryRepository.save(subCategory);
        return new CategoryCreateResult(saveResult.getId());
    }

    public List<SubCategory> loadByMainCategoryId(long mainCatId) {
        return subCategoryRepository.findByMainCategoryId(mainCatId);
    }

    @Transactional
    public AddProficientToSubCatResult addProficient(long proficientId, long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.getById(subCategoryId);
        Proficient proficient = proficientService.loadById(proficientId);

        subCategory.addProficient(proficient);

        SubCategory result = subCategoryRepository.save(subCategory);
        return AddProficientToSubCatResult.builder()
                .proficientId(proficientId)
                .subCategoryId(result.getId())
                .success(true)
                .build();
    }

    @Transactional
    public RemoveProficientFromSubCatResult removeProficient(long proficientId, long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.getById(subCategoryId);
        Proficient proficient = proficientService.loadById(proficientId);

        subCategory.removeProficient(proficient);
        proficient.removeSubCategory(subCategory);

        SubCategory result = subCategoryRepository.save(subCategory);
        return RemoveProficientFromSubCatResult.builder()
                .proficientId(proficientId)
                .subCategoryId(result.getId())
                .success(true)
                .build();
    }

    public SubCategory loadById(long id) {
        return subCategoryRepository.getById(id);
    }
}

