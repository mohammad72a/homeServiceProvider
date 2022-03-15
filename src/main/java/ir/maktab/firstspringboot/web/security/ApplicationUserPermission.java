package ir.maktab.firstspringboot.web.security;

public enum ApplicationUserPermission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    PROFICIENT_READ("proficient:read"),
    PROFICIENT_WRITE("proficient:write"),
    OFFER_READ("offer:read"),
    OFFER_WRITE("offer:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write"),
    REVIEW_READ("review:read"),
    REVIEW_WRITE("review:write"),
    MAIN_CATEGORY_READ("main_category:read"),
    MAIN_CATEGORY_WRITE("main_category:write"),
    SUB_CATEGORY_READ("sub_category:read"),
    SUB_CATEGORY_WRITE("sub_category:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
