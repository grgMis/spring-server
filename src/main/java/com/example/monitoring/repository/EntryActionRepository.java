package com.example.monitoring.repository;

import com.example.monitoring.model.EntryAction;
import com.example.monitoring.model.EntryActionState;
import com.example.monitoring.model.EntryActionType;
import com.example.monitoring.model.WellEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryActionRepository extends JpaRepository<EntryAction, Integer> {
    @Query("select e from EntryAction e where e.entryActionState = ?1")
    List<EntryAction> findByEntryActionState(EntryActionState entryActionState);
    @Query("select e from EntryAction e where e.entryActionType = ?1")
    List<EntryAction> findByEntryActionType(EntryActionType entryActionType);
    @Query("select e from EntryAction e where e.wellEquipment = ?1")
    List<EntryAction> findByWellEquipment(WellEquipment wellEquipment);

}
