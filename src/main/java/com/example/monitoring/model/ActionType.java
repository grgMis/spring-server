package com.example.monitoring.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "action_type")
public class ActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_type_id;

    @Column(name = "action_type_name")
    private String action_type_name;

    @Column(name = "action_type_sysname", nullable = false, length = 50)
    private String action_type_sysname;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action_group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActionGroup actionGroup;

    public ActionType() {
    }

    public Integer getAction_type_id() {
        return action_type_id;
    }

    public void setAction_type_id(Integer action_type_id) {
        this.action_type_id = action_type_id;
    }

    public String getAction_type_name() {
        return action_type_name;
    }

    public void setAction_type_name(String action_type_name) {
        this.action_type_name = action_type_name;
    }

    public String getAction_type_sysname() {
        return action_type_sysname;
    }

    public void setAction_type_sysname(String action_type_sysname) {
        this.action_type_sysname = action_type_sysname;
    }

    public ActionGroup getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(ActionGroup actionGroup) {
        this.actionGroup = actionGroup;
    }
}
