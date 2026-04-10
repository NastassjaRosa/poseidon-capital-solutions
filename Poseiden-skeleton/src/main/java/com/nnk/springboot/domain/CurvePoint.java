package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;


    @NotNull(message = "Curve id is mandatory")
    @Positive(message = "Curve id must be positive")
    @Column(name = "CurveId")
    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @Positive(message = "Term must be positive")
    @Column(name = "term")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Positive(message = "Value must be positive")
    @Column(name = "value")
    private Double value;

    public CurvePoint() {
    }

    public CurvePoint(int curveId, double term, double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}