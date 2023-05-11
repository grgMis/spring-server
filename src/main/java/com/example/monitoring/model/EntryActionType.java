package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "entry_action_type")
public class EntryActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entry_action_type_id;

    @Column(name = "entry_action_type_name", nullable = false, length = 50)
    private String entry_action_type_name;

    @Column(name = "entry_action_type_sysname", nullable = false, length = 50)
    private String entry_action_type_sysname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "entry_action_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntryActionGroup entryActionGroup;

    public EntryActionType() {
    }

    public Integer getEntry_action_type_id() {
        return entry_action_type_id;
    }

    public void setEntry_action_type_id(Integer entry_action_type_id) {
        this.entry_action_type_id = entry_action_type_id;
    }

    public String getEntry_action_type_name() {
        return entry_action_type_name;
    }

    public void setEntry_action_type_name(String entry_action_type_name) {
        this.entry_action_type_name = entry_action_type_name;
    }

    public String getEntry_action_type_sysname() {
        return entry_action_type_sysname;
    }

    public void setEntry_action_type_sysname(String entry_action_type_sysname) {
        this.entry_action_type_sysname = entry_action_type_sysname;
    }

    public EntryActionGroup getEntryActionGroup() {
        return entryActionGroup;
    }

    public void setEntryActionGroup(EntryActionGroup entryActionGroup) {
        this.entryActionGroup = entryActionGroup;
    }
}
