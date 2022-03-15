package ir.maktab.firstspringboot.api.category.main_category;

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
public class MainCategoryListResult {
    private List<MainCategoryModel> mainCategoryModels;

    public void addMainCategoryModel(MainCategoryModel mainCategoryModel) {
        if (mainCategoryModels == null) {
            mainCategoryModels = new ArrayList<>();
        }
        mainCategoryModels.add(mainCategoryModel);
    }
}