package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.exception.EmailException;
import ir.maktab.firstspringboot.exception.PasswordException;
import ir.maktab.firstspringboot.exception.ResourceNotFoundException;
import ir.maktab.firstspringboot.model.entity.user.Admin;
import ir.maktab.firstspringboot.util.Utility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private Utility utility;

    @Test
    void test_save_isOk() {
        Admin admin = new Admin();
        admin.setFirstName("First_Admin");
        admin.setLastName("Last_Admin");
        admin.setEmail("Email_Admin@mail.com");
        admin.setPassword("123asd");

        Admin result = adminService.saveAdmin(admin);
        assertNotNull(result);
    }

    @Test
    void test_save_with_duplicate_email() {
        Admin admin = new Admin();
        admin.setFirstName("First-1");
        admin.setLastName("Last-1");
        admin.setEmail("Email-1@mail.com");
        admin.setPassword("123asd");

        assertThrows(EmailException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_no_letter_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("12312345");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_no_number_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("aaaaaaaaa");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_save_with_not_min_length_pass() {
        Admin admin = new Admin();
        admin.setFirstName("First-4");
        admin.setLastName("Last-4");
        admin.setEmail("Email-4@mail.com");
        admin.setPassword("123ab");

        assertThrows(PasswordException.class, () -> adminService.save(admin));
    }

    @Test
    void test_change_password() {
        Admin admin = adminService.changePassword(1, "123asd", "456asd");
        assertEquals("456asd78", admin.getPassword());
    }

    @Test
    void test_change_password_with_wrong_old_pass() {
        assertThrows(PasswordException.class, () ->
                adminService.changePassword(1, "123asd", "456asd")
        );
    }

    @Test
    void test_change_password_with_invalid_new_pass() {
        assertThrows(PasswordException.class, () ->
                adminService.changePassword(1, "456asd", "123")
        );
    }

    @Test
    void test_load_by_email() {
        String email = "Email-1@mail.com";
        Admin result = adminService.loadByEmail(email);

        System.out.println(result);
        assertEquals("Email-1@mail.com", result.getEmail());
    }

    @Test
    void test_update_isOk() {
        long id = 5;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("First-3-updated");
        admin.setLastName("Last-3");
        admin.setEmail("Email-3@mail.com");
        admin.setPassword("123asd");
        Admin result = adminService.update(admin);

        assertEquals("First-3-updated", result.getFirstName());
    }

    @Test
    void test_update_with_duplicate_email() {
        long id = 5;
        Admin admin = new Admin();
        admin.setId(id);
        admin.setFirstName("First-3-updated");
        admin.setLastName("Last-3");
        admin.setEmail("Email-1@mail.com");
        admin.setPassword("123asd");

        assertThrows(EmailException.class, () -> adminService.update(admin));
    }

    @Test
    void test_load_by_id_exist() {
        long id = 1;
        Admin result = adminService.loadById(id);
        assertNotNull(result);
    }

    @Test
    void test_load_by_id_not_exist() {
        long id = 11;
        assertThrows(ResourceNotFoundException.class, () -> adminService.loadById(id));
    }

    @Test
    void test_load_all() {
        List<Admin> adminList = adminService.loadAll();
        assertEquals(3, adminList.size());
    }

    @Test
    void test_delete() {
        long id = 5;
        adminService.deleteById(id);

        List<Admin> adminList = adminService.loadAll();
        assertEquals(2, adminList.size());
    }

    @Test
    void test_delete_not_exist() {
        long id = 3;
        assertThrows(EmptyResultDataAccessException.class, () -> adminService.deleteById(id));
    }
}
