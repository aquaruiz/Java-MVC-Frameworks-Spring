package io.aquariuz.beerhub.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "beers")
@Getter
@Setter
@NoArgsConstructor
public class Beer extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private double ABV;

    @Column
    private String brewer;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column
    private String image;

    @Column
    private BigDecimal buyPrice;

    @Column
    private BigDecimal sellPrice;

    @Column
    private Long quantity;
}