package ir.maktab.firstspringboot.api.user.proficient;

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
public class ProficientListResult {
    private List<ProficientModel> proficientModels;

    public void addProficientModel(ProficientModel proficientModel) {
        if (proficientModels == null) {
            proficientModels = new ArrayList<>();
        }
        proficientModels.add(proficientModel);
    }
}
