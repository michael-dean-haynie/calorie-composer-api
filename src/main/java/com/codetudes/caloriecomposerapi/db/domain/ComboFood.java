package com.codetudes.caloriecomposerapi.db.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="combo_food")
public class ComboFood {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="is_draft")
    private Boolean isDraft;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Size(max = 100)
    @Column(name="description")
    private String description;

    @Valid
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="comboFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComboFoodFoodAmount> foodAmounts = new ArrayList();

    @Valid
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy="comboFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComboFoodPortion> portions = new ArrayList();

    public void setFoodAmounts(List<ComboFoodFoodAmount> foodAmounts) {
        if (null == this.foodAmounts){
            this.foodAmounts = foodAmounts;
        }
        this.foodAmounts.clear();
        this.foodAmounts.addAll(foodAmounts);
    }

    public void setPortions(List<ComboFoodPortion> portions) {
        if (null == this.portions){
            this.portions = portions;
        }
        this.portions.clear();
        this.portions.addAll(portions);
    }
}
