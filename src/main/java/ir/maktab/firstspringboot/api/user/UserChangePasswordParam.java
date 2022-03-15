package ir.maktab.firstspringboot.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordParam {
    private Long userId;
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
