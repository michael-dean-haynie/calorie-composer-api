package com.codetudes.caloriecomposerapi.db.domain;

import lombok.*;

import javax.persistence.*;
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

    @NotNull
    @Column(name="is_draft")
    private Boolean isDraft;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="draft_of")
    private Unit draftOf;

    @Size(max=45)
    @Column(name="singular_name")
    private String singular;

    @Size(max=45)
    @Column(name="plural_name")
    private String plural;

    @Column(name="abbreviation")
    private String abbreviation;

    @OneToOne(mappedBy="draftOf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Unit draft;
}
