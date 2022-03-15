package ir.maktab.firstspringboot.api.category.sub_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryCreateParam {
    private String name;
    private long mainCategoryId;
}
