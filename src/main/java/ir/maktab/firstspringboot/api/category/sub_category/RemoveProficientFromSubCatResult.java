package ir.maktab.firstspringboot.api.category.sub_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoveProficientFromSubCatResult {
    private Long subCategoryId;
    private Long proficientId;
    private boolean success;
}
