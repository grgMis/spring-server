package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "repair_action_type")
public class RepairActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repair_action_type_id;

    @Column(name = "repair_action_type_name")
    private String repair_action_type_name;

    @Column(name = "repair_action_type_sysname", nullable = false, length = 50)
    private String repair_action_type_sysname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "repair_action_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RepairActionGroup repairActionGroup;

    public RepairActionType() {
    }

    public Integer getRepair_action_type_id() {
        return repair_action_type_id;
    }

    public void setRepair_action_type_id(Integer repair_action_type_id) {
        this.repair_action_type_id = repair_action_type_id;
    }

    public String getRepair_action_type_name() {
        return repair_action_type_name;
    }

    public void setRepair_action_type_name(String repair_action_type_name) {
        this.repair_action_type_name = repair_action_type_name;
    }

    public String getRepair_action_type_sysname() {
        return repair_action_type_sysname;
    }

    public void setRepair_action_type_sysname(String repair_action_type_sysname) {
        this.repair_action_type_sysname = repair_action_type_sysname;
    }

    public RepairActionGroup getRepairActionGroup() {
        return repairActionGroup;
    }

    public void setRepairActionGroup(RepairActionGroup repairActionGroup) {
        this.repairActionGroup = repairActionGroup;
    }
}
