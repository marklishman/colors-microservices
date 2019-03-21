package io.lishman.purple.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dat_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "dat_value")
    private BigDecimal value;

    // TODO Automatically populate created date
    @Column(name = "dat_created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private Category category;

    public Data() {
    }

    // TODO constructor

    public Long getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }

    // TODO equals, hashcode, toString
}
