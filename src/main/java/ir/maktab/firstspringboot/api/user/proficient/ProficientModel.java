package ir.maktab.firstspringboot.api.user.proficient;

import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.entity.user.UserStatus;
import ir.maktab.firstspringboot.web.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProficientModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double credit;
    private Double ratingAvg;
    private UserStatus proficientStatus;
    private ApplicationUserRole userRole;
    private Instant registerDate;
    private String profileImg;

    public ProficientModel convertProficient2Model(Proficient proficient) {
        return ProficientModel.builder()
                .id(proficient.getId())
                .firstName(proficient.getFirstName())
                .lastName(proficient.getLastName())
                .email(proficient.getEmail())
                .password(proficient.getPassword())
                .credit(proficient.getCredit())
                .ratingAvg(proficient.getRatingAvg())
                .proficientStatus(proficient.getProficientStatus())
                .userRole(proficient.getApplicationUserRole())
                .registerDate(proficient.getRegisterDate())
                .profileImg(proficient.getProfileImage())
                .build();
    }
}
