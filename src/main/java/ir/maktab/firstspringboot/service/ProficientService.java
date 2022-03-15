package ir.maktab.firstspringboot.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import ir.maktab.firstspringboot.api.user.UserChangePasswordParam;
import ir.maktab.firstspringboot.api.user.UserChangePasswordResult;
import ir.maktab.firstspringboot.api.user.proficient.*;
import ir.maktab.firstspringboot.exception.EmailException;
import ir.maktab.firstspringboot.exception.PasswordException;
import ir.maktab.firstspringboot.model.entity.ConfirmationToken;
import ir.maktab.firstspringboot.model.entity.user.Proficient;
import ir.maktab.firstspringboot.model.entity.user.UserStatus;
import ir.maktab.firstspringboot.model.repository.ProficientRepository;
import ir.maktab.firstspringboot.util.Utility;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ir.maktab.firstspringboot.web.security.ApplicationUserRole.PROFICIENT;


@Service
public class ProficientService {
    private final ProficientRepository proficientRepository;
    private final Utility utility;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailValidatorService emailValidatorService;
    private final ConfirmationTokenService confirmationTokenService;

    public ProficientService(ProficientRepository proficientRepository, Utility utility, BCryptPasswordEncoder bCryptPasswordEncoder, EmailValidatorService emailValidatorService, ConfirmationTokenService confirmationTokenService) {
        this.proficientRepository = proficientRepository;
        this.utility = utility;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailValidatorService = emailValidatorService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public ProficientModel save(ProficientCreateParam createParam) throws IOException {

        boolean isValidEmail = emailValidatorService.test(createParam.getEmail());

        if (!isValidEmail) {
            throw new EmailException(String.format("Email: %s, is not valid", createParam.getEmail()));
        }

        Proficient loadByEmail = loadByEmail(createParam.getEmail());
        if (loadByEmail != null) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(createParam.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }

        Proficient proficient = createParam.convert2Proficient();
        proficient.setPassword(bCryptPasswordEncoder.encode(createParam.getPassword()));
        proficient.setProficientStatus(UserStatus.NEW);
        proficient.setApplicationUserRole(PROFICIENT);
        proficient.setCredit(0.0);

        Proficient saveResult = proficientRepository.save(proficient);

        String token = utility.createRandomToken();
        ConfirmationToken confirmationToken = utility.createConfirmationToken(saveResult, token);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = "http://localhost:8080/proficients/confirm?token=" + token;


        return new ProficientModel().convertProficient2Model(saveResult);
    }

    public Proficient update(Proficient proficient) {
        Proficient loadByEmail = loadByEmail(proficient.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), proficient.getId())) {
            throw new EmailException("Another proficient with this email already exists");
        }

        if (utility.passwordIsNotValid(proficient.getPassword())) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setProficientStatus(UserStatus.AWAITING_APPROVAL);
        return proficientRepository.save(proficient);
    }

    public ProficientUpdateResult updateProficient(ProficientUpdateParam updateParam) throws IOException {
        Proficient proficient = updateParam.convert2Proficient();

        Proficient proficientInDb = proficientRepository.getById(proficient.getId());
        if (!proficientInDb.getPassword().equals(proficient.getPassword())) {
            throw new PasswordException("Password is incorrect");
        }

        Proficient loadByEmail = loadByEmail(proficient.getEmail());
        if (loadByEmail != null && !Objects.equals(loadByEmail.getId(), proficient.getId())) {
            throw new EmailException("Another proficient with this email already exists");
        }

        proficient.setApplicationUserRole(PROFICIENT);
        proficient.setProficientStatus(UserStatus.AWAITING_APPROVAL);
        Proficient result = proficientRepository.save(proficient);

        return ProficientUpdateResult.builder()
                .id(result.getId())
                .success(true)
                .build();
    }

    @Transactional
    public UserChangePasswordResult changePassword(UserChangePasswordParam changePasswordParam) {
        long proficientId = changePasswordParam.getUserId();
        String oldPass = changePasswordParam.getCurrentPassword();
        String newPass = changePasswordParam.getNewPassword();
        String confirmNewPass = changePasswordParam.getNewPasswordConfirm();

        if (!newPass.equals(confirmNewPass)) {
            throw new PasswordException("New password and confirm password doesn't match");
        }
        Proficient proficient = proficientRepository.getById(proficientId);
        if (!Objects.equals(proficient.getPassword(), oldPass)) {
            throw new PasswordException("Old password doesn't match");
        }
        if (utility.passwordIsNotValid(newPass)) {
            throw new PasswordException("Password length must be at least 8 character and contain letters and numbers");
        }
        proficient.setPassword(newPass);
        Proficient result = proficientRepository.save(proficient);
        return new UserChangePasswordResult(result.getId(), result.getPassword());
    }


    public ProficientListResult loadAllProficients() {
        List<Proficient> proficientList = proficientRepository.findAll();
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public ProficientListResult loadAllProficientsByStatus(UserStatus status) {
        List<Proficient> proficientList = proficientRepository.findAllByProficientStatus(status);
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public ProficientListResult loadAllProficientsBySubCategoryId(long subCategoryId) {
        List<Proficient> proficientList = proficientRepository.findAllBySubCategoriesId(subCategoryId);
        ProficientListResult proficientListResult = new ProficientListResult();
        proficientList.forEach((p) -> proficientListResult.addProficientModel(
                new ProficientModel().convertProficient2Model(p)));
        return proficientListResult;
    }

    public Proficient loadByEmail(String email) {
        return proficientRepository.findByEmail(email);
    }

    public Proficient loadById(long proficientId) {
        return proficientRepository.getById(proficientId);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiresAt = confirmationToken.getExpiresAt();
        if (expiresAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token is expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableUser(confirmationToken.getUser().getEmail());

        return String.format("Email: %s confirmed", confirmationToken.getUser().getEmail());
    }

    private void enableUser(String email) {
        Proficient user = proficientRepository.findByEmail(email);
        user.setEnabled(true);
        user.setProficientStatus(UserStatus.AWAITING_APPROVAL);
        proficientRepository.save(user);
    }

    public ProficientUpdateResult confirmProficientByAdmin(long userId) {
        Proficient proficient = proficientRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("user not found"));
        proficient.setProficientStatus(UserStatus.CONFIRMED);
        proficient.setLocked(false);
        Proficient updateResult = proficientRepository.save(proficient);
        return new ProficientUpdateResult(updateResult.getId(), true);
    }

    public Iterable<Proficient> findAll(BooleanExpression exp) {
        return proficientRepository.findAll(exp);
    }
}