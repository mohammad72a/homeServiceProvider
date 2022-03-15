package ir.maktab.firstspringboot.api.user.proficient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProficientUpdateResult {
    private Long id;
    private boolean success;
}