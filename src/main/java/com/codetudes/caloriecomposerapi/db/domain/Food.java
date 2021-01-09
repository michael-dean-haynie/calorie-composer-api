package com.codetudes.caloriecomposerapi.db.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Column(name="is_draft")
    private Boolean isDraft;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="draft_of")
    private Food draftOf;

    @Column(name="fdc_id")
    private String fdcId;

    @Column(name="description")
    private String description;

    @Column(name="brand_owner")
    private String brandOwner;

    @Column(name="ingredients")
    private String ingredients;

    @Valid
    @ManyToOne()
    @JoinColumn(name="ssr_display_unit_id")
    private Unit ssrDisplayUnit;

    @Valid
    @ManyToOne()
    @JoinColumn(name="csr_display_unit_id")
    private Unit csrDisplayUnit;

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

    @OneToOne(mappedBy="draftOf", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Food draft;

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
