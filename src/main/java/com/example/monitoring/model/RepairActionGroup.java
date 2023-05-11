package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "repair_action_group")
public class RepairActionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repair_action_group_id;

    @Column(name = "repair_action_group_name", nullable = false, length = 50)
    private String repair_action_group_name;

    @Column(name = "repair_action_group_sysname", length = 20)
    private String repair_action_group_sysname;

    public RepairActionGroup() {
    }

    public RepairActionGroup(String repair_action_group_name, String repair_action_group_sysname) {
        this.repair_action_group_name = repair_action_group_name;
        this.repair_action_group_sysname = repair_action_group_sysname;
    }

    public Integer getRepair_action_group_id() {
        return repair_action_group_id;
    }

    public void setRepair_action_group_id(Integer repair_action_group_id) {
        this.repair_action_group_id = repair_action_group_id;
    }

    public String getRepair_action_group_name() {
        return repair_action_group_name;
    }

    public void setRepair_action_group_name(String repair_action_group_name) {
        this.repair_action_group_name = repair_action_group_name;
    }

    public String getRepair_action_group_sysname() {
        return repair_action_group_sysname;
    }

    public void setRepair_action_group_sysname(String repair_action_group_sysname) {
        this.repair_action_group_sysname = repair_action_group_sysname;
    }
}
