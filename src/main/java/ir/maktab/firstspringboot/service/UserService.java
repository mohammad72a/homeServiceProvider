package ir.maktab.firstspringboot.service;

import ir.maktab.firstspringboot.model.entity.user.User;
import ir.maktab.firstspringboot.model.repository.UserRepository;
import ir.maktab.firstspringboot.web.security.auth.ApplicationUser;
import ir.maktab.firstspringboot.web.security.auth.ApplicationUserDao;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements ApplicationUserDao {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        User user = userRepository.findByEmail(username);

        ApplicationUser applicationUser = new ApplicationUser(
                user.getId(),
                user.getApplicationUserRole().getGrantedAuthorities(),
                user.getPassword(),
                user.getEmail(),
                true,
                user.isAccountNonLocked(),
                true,
                user.isEnabled()
        );
        return Optional.of(applicationUser);
    }
}
