package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "entry_action")
public class EntryAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entry_action_id;

    @Column(name = "date_begin", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_begin;

    @Column(name = "date_end")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_end;

    @Column(name = "depth_begin")
    private Long depth_begin;

    @Column(name = "depth_end")
    private Long depth_end;

    @Column(name = "depth_length")
    private Long depth_length;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_equipment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WellEquipment wellEquipment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_action_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntryActionType entryActionType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_action_state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntryActionState entryActionState;

    public EntryAction() {
    }

    public Integer getEntry_action_id() {
        return entry_action_id;
    }

    public void setEntry_action_id(Integer entry_action_id) {
        this.entry_action_id = entry_action_id;
    }

    public Date getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(Date date_begin) {
        this.date_begin = date_begin;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public Long getDepth_begin() {
        return depth_begin;
    }

    public void setDepth_begin(Long depth_begin) {
        this.depth_begin = depth_begin;
    }

    public Long getDepth_end() {
        return depth_end;
    }

    public void setDepth_end(Long depth_end) {
        this.depth_end = depth_end;
    }

    public Long getDepth_length() {
        return depth_length;
    }

    public void setDepth_length(Long depth_begin, Long depth_end) {
        this.depth_length = depth_end - depth_begin;
    }

    public WellEquipment getWellEquipment() {
        return wellEquipment;
    }

    public void setWellEquipment(WellEquipment wellEquipment) {
        this.wellEquipment = wellEquipment;
    }

    public EntryActionType getEntryActionType() {
        return entryActionType;
    }

    public void setEntryActionType(EntryActionType entryActionType) {
        this.entryActionType = entryActionType;
    }

    public EntryActionState getEntryActionState() {
        return entryActionState;
    }

    public void setEntryActionState(EntryActionState entryActionState) {
        this.entryActionState = entryActionState;
    }
}
