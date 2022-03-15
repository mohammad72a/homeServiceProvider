package ir.maktab.firstspringboot.model.entity.category;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class MainCategory extends Category {

    @OneToMany(mappedBy = "mainCategory")
    private Set<SubCategory> subCategorySet;

    public void addSubCategory(SubCategory subCategory) {
        if (subCategorySet == null) {
            subCategorySet = new HashSet<>();
        }
        subCategorySet.add(subCategory);
        subCategory.setMainCategory(this);
    }
}

