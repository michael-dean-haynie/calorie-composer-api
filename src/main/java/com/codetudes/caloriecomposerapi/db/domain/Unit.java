package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="unit")
public class Unit {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NotBlank
    @Size(max=45)
    @Column(name="singular_name")
    private String singular;

    @NotBlank
    @Size(max=45)
    @Column(name="plural_name")
    private String plural;

    @Size(max=45)
    @Column(name="abbreviation")
    private String abbreviation;

    @NotNull
    @Column(name="is_custom")
    private Boolean isCustom;
}
