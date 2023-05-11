package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "repair_action")
public class RepairAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repair_action_id;

    @Column(name = "date_begin", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_begin;

    @Column(name = "date_end")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_end;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_equipment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private WellEquipment wellEquipment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_action_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RepairActionType repairActionType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_action_state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RepairActionState repairActionState;

    public RepairAction() {
    }

    public Integer getRepair_action_id() {
        return repair_action_id;
    }

    public void setRepair_action_id(Integer repair_action_id) {
        this.repair_action_id = repair_action_id;
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

    public WellEquipment getWellEquipment() {
        return wellEquipment;
    }

    public void setWellEquipment(WellEquipment wellEquipment) {
        this.wellEquipment = wellEquipment;
    }

    public RepairActionType getRepairActionType() {
        return repairActionType;
    }

    public void setRepairActionType(RepairActionType repairActionType) {
        this.repairActionType = repairActionType;
    }

    public RepairActionState getRepairActionState() {
        return repairActionState;
    }

    public void setRepairActionState(RepairActionState repairActionState) {
        this.repairActionState = repairActionState;
    }
}
