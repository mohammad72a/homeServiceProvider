package ir.maktab.firstspringboot.api.category.sub_category;

import ir.maktab.firstspringboot.model.entity.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryModel {
    private Long id;
    private String name;
    private Long mainCategoryId;

    public SubCategoryModel convertSubMainCat2Model(SubCategory subCategory) {
        return SubCategoryModel.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .mainCategoryId(subCategory.getMainCategory().getId())
                .build();
    }
}
