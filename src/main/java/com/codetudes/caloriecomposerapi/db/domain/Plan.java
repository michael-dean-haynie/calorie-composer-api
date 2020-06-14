package com.codetudes.caloriecomposerapi.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name="date")
    private Date date;
}
