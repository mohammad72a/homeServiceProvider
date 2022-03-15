package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.api.user.UserChangePasswordParam;
import ir.maktab.firstspringboot.api.user.UserChangePasswordResult;
import ir.maktab.firstspringboot.api.user.proficient.ProficientCreateParam;
import ir.maktab.firstspringboot.api.user.proficient.ProficientModel;
import ir.maktab.firstspringboot.exception.PasswordException;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProficientServiceTest {

    @Autowired
    private ProficientService proficientService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Test
    void save() throws IOException {

        ProficientCreateParam createParam = ProficientCreateParam.builder()
                .firstName("First-Proficient-9")
                .lastName("Last-Proficient-9")
                .email("Email-Proficient-9@mail.com")
                .password("12345678asd")
//                .userRole(ApplicationUserRole.PROFICIENT)
                .build();

        ProficientModel result = proficientService.save(createParam);
        System.out.println(result.getProfileImg());
    }

    @Test
    void test_change_password() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("456asd78")
                .newPassword("12345678asd")
                .newPasswordConfirm("12345678asd")
                .build();

        UserChangePasswordResult result = proficientService.changePassword(changePasswordParam);
        assertEquals("12345678asd", result.getNewPassword());
    }

    @Test
    void test_change_password_with_wrong_old_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("456asd78")
                .newPassword("12345678asd")
                .newPasswordConfirm("12345678asd")
                .build();

        assertThrows(PasswordException.class, () ->
                proficientService.changePassword(changePasswordParam)
        );
    }

    @Test
    void test_change_password_with_invalid_new_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("12345678asd")
                .newPassword("123")
                .newPasswordConfirm("123")
                .build();

        assertThrows(PasswordException.class, () ->
                proficientService.changePassword(changePasswordParam)
        );
    }

    @Test
    void test_change_password_with_not_confirmed_new_pass() {
        UserChangePasswordParam changePasswordParam = UserChangePasswordParam.builder()
                .userId(1L)
                .currentPassword("12345678asd")
                .newPassword("123asd456")
                .newPasswordConfirm("123fgh456")
                .build();

        assertThrows(PasswordException.class, () ->
                proficientService.changePassword(changePasswordParam)
        );
    }

    @Test
    void loadByEmail() {
        Proficient proficient = proficientService.loadByEmail("Email-Proficient-2@mail.com");
        assertEquals(2L, proficient.getId());
    }
}
