package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
public class Nutrient {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="food_id", nullable = false)
    private Food food;

    @NotBlank
    @Size(max=45)
    @Column(name="name")
    private String name;

    @NotBlank
    @Size(max=45)
    @Column(name="unit_name")
    private String unitName;

    @NotNull
    @DecimalMax("999.99")
    @Column(name="amount")
    private BigDecimal amount;
}
