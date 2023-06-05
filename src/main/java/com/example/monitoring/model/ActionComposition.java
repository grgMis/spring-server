package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "action_composition")
public class ActionComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_composition_id;

    @Column(name = "date_complete")
    private Date date_complete;

    @Column(name = "action_composition_note", length = 100)
    private String action_composition_note;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Action action;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "equipment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action_composition_state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActionCompositionState actionCompositionState;

    public ActionComposition() {
    }

    public Integer getAction_composition_id() {
        return action_composition_id;
    }

    public void setAction_composition_id(Integer action_composition_id) {
        this.action_composition_id = action_composition_id;
    }

    public Date getDate_complete() {
        return date_complete;
    }

    public void setDate_complete(Date date_complete) {
        this.date_complete = date_complete;
    }

    public String getAction_composition_note() {
        return action_composition_note;
    }

    public void setAction_composition_note(String action_composition_note) {
        this.action_composition_note = action_composition_note;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public ActionCompositionState getActionCompositionState() {
        return actionCompositionState;
    }

    public void setActionCompositionState(ActionCompositionState actionCompositionState) {
        this.actionCompositionState = actionCompositionState;
    }
}
