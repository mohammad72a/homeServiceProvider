package ir.maktab.firstspringboot.web.controller;

import ir.maktab.firstspringboot.api.user.UserChangePasswordParam;
import ir.maktab.firstspringboot.api.user.UserChangePasswordResult;
import ir.maktab.firstspringboot.api.user.proficient.ProficientCreateParam;
import ir.maktab.firstspringboot.api.user.proficient.ProficientModel;
import ir.maktab.firstspringboot.api.user.proficient.ProficientUpdateParam;
import ir.maktab.firstspringboot.api.user.proficient.ProficientUpdateResult;
import ir.maktab.firstspringboot.service.ProficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/proficients")
public class ProficientController {

    private final ProficientService proficientService;

    @PostMapping
    public ResponseEntity<ProficientModel> save(@ModelAttribute ProficientCreateParam createParam) throws IOException {
        ProficientModel response = proficientService.save(createParam);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<UserChangePasswordResult> changePassword(@RequestBody UserChangePasswordParam changePasswordParam,
                                                                   @PathVariable Long id) {
        changePasswordParam.setUserId(id);
        UserChangePasswordResult userChangePasswordResult = proficientService.changePassword(changePasswordParam);
        return ResponseEntity.ok(userChangePasswordResult);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_PROFICIENT')")
    public ResponseEntity<ProficientUpdateResult> update(@PathVariable long id, @RequestBody ProficientUpdateParam updateParam) {
        updateParam.setId(id);
        ProficientUpdateResult proficientUpdateResult = null;
        try {
            proficientUpdateResult = proficientService.updateProficient(updateParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(proficientUpdateResult);
    }

    //    http://localhost:8080/proficients/confirm?token
    @GetMapping(path = {"confirm"})
    public String confirmToken(@RequestParam("token") String token) {
        return proficientService.confirmToken(token);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{proficientId}/confirm")
    public ResponseEntity<ProficientUpdateResult> confirmProficientByAdmin(@PathVariable long proficientId) {
        ProficientUpdateResult proficientUpdateResult = proficientService.confirmProficientByAdmin(proficientId);
        return ResponseEntity.ok(proficientUpdateResult);
    }
}

