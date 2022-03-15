package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.exception.EmailException;
import ir.maktab.firstspringboot.exception.PasswordException;
import ir.maktab.firstspringboot.model.entity.user.Admin;
import ir.maktab.firstspringboot.model.repository.AdminRepository;
import ir.maktab.firstspringboot.util.Utility;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import static ir.maktab.firstspringboot.web.security.ApplicationUserRole.ADMIN;


@Service
public class AdminService extends BaseService<Admin, Long> {
    private final AdminRepository adminRepository;
    private final Utility utility;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminService(AdminRepository adminRepository, Utility utility, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.utility = utility;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        setJpaRepository(adminRepository);
    }

    public Admin saveAdmin(Admin admin) {
        Admin loadByEmail = loadByEmail(admin.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another admin with this email already exists");
        }

        if (utility.passwordIsNotValid(admin.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }

        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        admin.setApplicationUserRole(ADMIN);
        admin.setLocked(false);
        admin.setEnabled(true);
        return super.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        Admin loadByEmail = loadByEmail(admin.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), admin.getId())) {
            throw new EmailException("Another admin with this email already exists");
        }

        if (utility.passwordIsNotValid(admin.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        return super.update(admin);
    }

    @Transactional
    public Admin changePassword(long adminId, String oldPass, String newPass) {
        Admin admin = adminRepository.getById(adminId);
        if (!Objects.equals(admin.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        admin.setPassword(newPass);
        return super.update(admin);
    }

    public Admin loadByEmail(String email) {
        return adminRepository.findByEmail(email);
    }
}