package ir.maktab.firstspringboot.api.category.main_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainCategoryCreateParam {
    private String name;
}
