package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "action_group")
public class ActionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_group_id;

    @Column(name = "action_group_name", nullable = false, length = 50)
    private String action_group_name;

    @Column(name = "action_group_sysname", length = 20)
    private String action_group_sysname;

    public ActionGroup() {
    }

    public ActionGroup(String action_group_name, String action_group_sysname) {
        this.action_group_name = action_group_name;
        this.action_group_sysname = action_group_sysname;
    }

    public Integer getAction_group_id() {
        return action_group_id;
    }

    public void setAction_group_id(Integer action_group_id) {
        this.action_group_id = action_group_id;
    }

    public String getAction_group_name() {
        return action_group_name;
    }

    public void setAction_group_name(String action_group_name) {
        this.action_group_name = action_group_name;
    }

    public String getAction_group_sysname() {
        return action_group_sysname;
    }

    public void setAction_group_sysname(String action_group_sysname) {
        this.action_group_sysname = action_group_sysname;
    }
}
