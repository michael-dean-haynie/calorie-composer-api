package com.codetudes.caloriecomposerapi.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="food")
public class Food {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Size(max = 10)
    @Column(name="fdc_id")
    private String fdcId;

    @Size(max = 100)
    @NotBlank
    @Column(name="description")
    private String description;

    @Size(max = 100)
    @Column(name="brand_owner")
    private String brandOwner;

    @Size(max = 10000)
    @Column(name="ingredients")
    private String ingredients;

    @Valid
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="food", cascade = CascadeType.ALL, orphanRemoval = true)
    // default to empty array list to appease hibernate state management voodoo
    private List<Nutrient> nutrients = new ArrayList();

    @Valid
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="food", cascade = CascadeType.ALL, orphanRemoval = true)
    // default to empty array list to appease hibernate state management voodoo
    private List<ConversionRatio> conversionRatios = new ArrayList();

    // opt to swap out contents instead of re-assigning collection
    // this to appease the hibernate state management voodoo
    public void setNutrients(List<Nutrient> nutrients) {
        if (null == this.nutrients){
            this.nutrients = nutrients;
        }
        this.nutrients.clear();
        this.nutrients.addAll(nutrients);
    }

    // opt to swap out contents instead of re-assigning collection
    // this to appease the hibernate state management voodoo
    public void setConversionRatios(List<ConversionRatio> conversionRatios) {
        if (null == this.conversionRatios){
            this.conversionRatios = conversionRatios;
        }
        this.conversionRatios.clear();
        this.conversionRatios.addAll(conversionRatios);
    }
}
