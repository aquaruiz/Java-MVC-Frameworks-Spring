package io.bar.beerhub.web.models;

import io.bar.beerhub.services.models.RoleServiceModel;

import java.util.Set;

public class UserListingModel {
    private String id;
    private String username;
    private Set<RoleServiceModel> authorities;

    public UserListingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
