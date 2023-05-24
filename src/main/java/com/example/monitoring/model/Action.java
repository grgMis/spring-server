package com.example.monitoring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer action_id;

    @Column(name = "date_entry")
    @DateTimeFormat(pattern = "dd.MM.yyy")
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_entry;

    @Column(name = "date_begin")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_begin;

    @Column(name = "date_end")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date date_end;

    @Column(name = "action_note", length = 300)
    private String action_note;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "well_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Well well;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActionType actionType;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "action_state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActionState actionState;

    public Action() {
    }

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public Date getDate_entry() {
        return date_entry;
    }

    public void setDate_entry(Date date_entry) {
        this.date_entry = date_entry;
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

    public String getAction_note() {
        return action_note;
    }

    public void setAction_note(String action_note) {
        this.action_note = action_note;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public void setActionState(ActionState actionState) {
        this.actionState = actionState;
    }
}
