package ir.maktab.firstspringboot.model.entity.category;

import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.model.entity.order.HomeServiceOrder;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class SubCategory extends Category {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private MainCategory mainCategory;

    @OneToMany(mappedBy = "subCategory")
    private Set<HomeServiceOrder> homeServiceOrders;

    @ManyToMany(mappedBy = "subCategories", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Proficient> proficients;

    public void addProficient(Proficient proficient) {
        if (proficients == null) {
            proficients = new HashSet<>();
        }
        proficients.add(proficient);

        proficient.addSubCategory(this);
    }

    public void removeProficient(Proficient proficient){
        if (proficients == null) {
            throw new ResourceNotFoundException("proficient","id", proficient.getId());
        }
        proficients.remove(proficient);
        proficient.removeSubCategory(this);
    }
}
