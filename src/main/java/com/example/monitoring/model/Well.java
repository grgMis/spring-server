package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "well")
public class Well {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer well_id;

    @Column(name = "well_name", nullable = false, length = 40)
    private String well_name;

    @Column(name = "date_entry", nullable = false)
    private Date date_entry;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WellState wellState;

    public Well() {
    }

    public Integer getWell_id() {
        return well_id;
    }

    public void setWell_id(Integer well_id) {
        this.well_id = well_id;
    }

    public String getWell_name() {
        return well_name;
    }

    public void setWell_name(String well_name) {
        this.well_name = well_name;
    }

    public Date getDate_entry() {
        return date_entry;
    }

    public void setDate_entry(Date date) {
        this.date_entry = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public WellState getWellState() {
        return wellState;
    }

    public void setWellState(WellState wellState) {
        this.wellState = wellState;
    }
}
