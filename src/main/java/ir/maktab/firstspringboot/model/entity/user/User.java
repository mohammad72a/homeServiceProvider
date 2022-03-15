package ir.maktab.firstspringboot.model.entity.user;

import ir.maktab.firstspringboot.web.security.ApplicationUserRole;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email; // Must be unique

    @Column(nullable = false)
    private String password; // Minimum size:8, combination of letters and numbers

    @Column(nullable = false)
    private ApplicationUserRole applicationUserRole;

    @Builder.Default
    private Boolean locked = true;

    @Builder.Default
    private Boolean enabled = false;

    public boolean isAccountNonLocked() {
        return !locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
