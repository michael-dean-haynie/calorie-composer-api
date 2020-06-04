package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

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
}
