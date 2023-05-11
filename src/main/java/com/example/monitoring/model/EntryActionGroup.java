package com.example.monitoring.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entry_action_group")
public class EntryActionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entry_action_group_id;

    @Column(name = "entry_action_group_name", nullable = false, length = 50)
    private String entry_action_group_name;

    @Column(name = "entry_action_group_sysname", length = 20)
    private String entry_action_group_sysname;

    public EntryActionGroup() {
    }

    public EntryActionGroup(String entry_action_group_name, String entry_action_group_sysname) {
        this.entry_action_group_name = entry_action_group_name;
        this.entry_action_group_sysname = entry_action_group_sysname;
    }

    public Integer getEntry_action_group_id() {
        return entry_action_group_id;
    }

    public void setEntry_action_group_id(Integer entry_action_group_id) {
        this.entry_action_group_id = entry_action_group_id;
    }

    public String getEntry_action_group_name() {
        return entry_action_group_name;
    }

    public void setEntry_action_group_name(String entry_action_group_name) {
        this.entry_action_group_name = entry_action_group_name;
    }

    public String getEntry_action_group_sysname() {
        return entry_action_group_sysname;
    }

    public void setEntry_action_group_sysname(String entry_action_group_sysname) {
        this.entry_action_group_sysname = entry_action_group_sysname;
    }
}
