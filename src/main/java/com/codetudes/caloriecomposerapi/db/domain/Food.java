package com.codetudes.caloriecomposerapi.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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

    @Size(max = 500)
    @Column(name="ingredients")
    private String ingredients;

    @DecimalMax("999.99")
    @Column(name="serving_size")
    private BigDecimal servingSize;

    @Size(max=45)
    @Column(name="serving_size_unit")
    private String servingSizeUnit;

    @Size(max=100)
    @Column(name="household_serving_full_text")
    private String householdServingFullText;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="food", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    // default to empty array list to appease hibernate state management voodoo
    private List<Nutrient> nutrients = new ArrayList();

    // opt to swap out contents instead of re-assigning collection
    // this to appease the hibernate state management voodoo
    public void setNutrients(List<Nutrient> nutrients) {
        if (null == this.nutrients){
            this.nutrients = nutrients;
        }
        this.nutrients.clear();
        this.nutrients.addAll(nutrients);
    }
}
