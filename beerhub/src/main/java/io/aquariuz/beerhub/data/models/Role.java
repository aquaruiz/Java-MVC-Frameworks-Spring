package io.aquariuz.beerhub.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {
    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }
}
