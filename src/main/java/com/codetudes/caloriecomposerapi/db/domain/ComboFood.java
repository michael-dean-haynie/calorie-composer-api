//package com.codetudes.caloriecomposerapi.db.domain;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Setter;
//
//import javax.persistence.*;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
//@Table(name="combo_food")
//public class ComboFood {
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    private User user;
//
//    @NotNull
//    @Column(name="is_draft")
//    private Boolean isDraft;
//
//    @ManyToOne
//    @JoinColumn(name="draft_of_combo_food_id")
//    private ComboFood draftOf;
//
//    @Size(max = 100)
//    @Column(name="description")
//    private String description;
//
//    @Valid
//    @Setter(AccessLevel.NONE)
//    @OneToMany(mappedBy="comboFood", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ComboFoodConversionRatio> conversionRatios = new ArrayList();
//
//    @Valid
//    @Setter(AccessLevel.NONE)
//    @OneToMany(mappedBy="comboFood", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ComboFoodConstituent> constituents = new ArrayList();
//
//    public void setConversionRatios(List<ComboFoodConversionRatio> conversionRatios) {
//        if (null == this.conversionRatios){
//            this.conversionRatios = conversionRatios;
//        }
//        this.conversionRatios.clear();
//        this.conversionRatios.addAll(conversionRatios);
//    }
//
//    public void setConstituents(List<ComboFoodConstituent> constituents) {
//        if (null == this.constituents){
//            this.constituents = constituents;
//        }
//        this.constituents.clear();
//        this.constituents.addAll(constituents);
//    }
//}
