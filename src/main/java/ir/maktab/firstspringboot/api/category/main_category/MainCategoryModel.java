package ir.maktab.firstspringboot.api.category.main_category;

import ir.maktab.firstspringboot.api.category.sub_category.SubCategoryModel;
import ir.maktab.firstspringboot.model.entity.category.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainCategoryModel {
    private Long id;
    private String name;
    private List<SubCategoryModel> subCategoryModels;

    public MainCategoryModel convertMainCat2Model(MainCategory mainCategory) {
        List<SubCategoryModel> subCategoryModels = new ArrayList<>();
        mainCategory.getSubCategorySet().forEach(s -> subCategoryModels.add(new SubCategoryModel().convertSubMainCat2Model(s)));
        return MainCategoryModel.builder()
                .id(mainCategory.getId())
                .name(mainCategory.getName())
                .subCategoryModels(subCategoryModels)
                .build();
    }
}
