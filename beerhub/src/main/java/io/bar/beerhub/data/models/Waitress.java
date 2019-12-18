package io.bar.beerhub.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Waitress extends BaseEntity {
    private String name;
    private String image;
    private Double tipsRate;

    public Waitress() {
    }

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column
    public Double getTipsRate() {
        return tipsRate;
    }

    public void setTipsRate(Double tipsRate) {
        this.tipsRate = tipsRate;
    }
}
