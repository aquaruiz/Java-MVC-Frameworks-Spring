package io.aquariuz.beerhub.services.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BeerServiceModel extends BaseServiceModel {
    private String name;
    private double abv;
    private String brewer;
    private String description;
    private String image;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private Long quantity;

}
