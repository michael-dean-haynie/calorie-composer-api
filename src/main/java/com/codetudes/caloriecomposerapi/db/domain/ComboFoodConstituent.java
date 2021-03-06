//package com.codetudes.caloriecomposerapi.db.domain;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//
//import javax.persistence.*;
//import javax.validation.constraints.DecimalMax;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.math.BigDecimal;
//
//@Data
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Entity
//@Table(name="combo_food_constituent")
//public class ComboFoodConstituent {
//    @EqualsAndHashCode.Include
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//
//    @ToString.Exclude
//    @NotNull
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="combo_food_id")
//    private ComboFood comboFood;
//
//    @ToString.Exclude
//    @NotNull
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="food_id")
//    private Food food;
//
//    @NotBlank
//    @Size(max=45)
//    @Column(name="unit")
//    private String unit;
//
//    @NotNull
//    @DecimalMax("999.99")
//    @Column(name="amount")
//    private BigDecimal amount;
//}
