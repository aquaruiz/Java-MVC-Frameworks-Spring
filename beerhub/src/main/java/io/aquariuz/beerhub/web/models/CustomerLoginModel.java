package io.aquariuz.beerhub.web.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerLoginModel {
    private String id;
    private String username;
    private String password;
}
