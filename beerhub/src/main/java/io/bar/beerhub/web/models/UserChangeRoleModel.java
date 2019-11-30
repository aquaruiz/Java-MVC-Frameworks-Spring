package io.bar.beerhub.web.models;

public class UserChangeRoleModel {
    private String userId;
    private String roleName;

    public UserChangeRoleModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
