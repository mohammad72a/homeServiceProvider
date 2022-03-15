package ir.maktab.firstspringboot.web.security.auth;

import java.util.Optional;

public interface ApplicationUserDao{
    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}

