package ir.maktab.firstspringboot.web.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;
import static ir.maktab.firstspringboot.web.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, PROFICIENT_READ, PROFICIENT_WRITE,
            ORDER_READ, OFFER_READ, MAIN_CATEGORY_READ, MAIN_CATEGORY_WRITE, SUB_CATEGORY_READ, SUB_CATEGORY_WRITE)),

    CUSTOMER(Sets.newHashSet(CUSTOMER_READ, CUSTOMER_WRITE, ORDER_READ, ORDER_WRITE,
            OFFER_READ, REVIEW_READ, REVIEW_WRITE, MAIN_CATEGORY_READ, SUB_CATEGORY_READ)),

    PROFICIENT(Sets.newHashSet(PROFICIENT_READ, PROFICIENT_WRITE, ORDER_READ,
            OFFER_READ, OFFER_WRITE, MAIN_CATEGORY_READ, SUB_CATEGORY_READ, SUB_CATEGORY_WRITE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
