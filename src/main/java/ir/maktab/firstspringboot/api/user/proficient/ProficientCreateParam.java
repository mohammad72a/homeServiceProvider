package ir.maktab.firstspringboot.api.user.proficient;

import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.web.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProficientCreateParam {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ApplicationUserRole userRole;
    private MultipartFile profileImgFile;

    public Proficient convert2Proficient() throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(profileImgFile.getOriginalFilename()));
        if (fileName.contains("..")) {
            System.out.println("not a valid file");
        }
        return Proficient.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .applicationUserRole(this.userRole)
                .profileImage(Base64.getEncoder().encodeToString(profileImgFile.getBytes()))
                .build();
    }
}
