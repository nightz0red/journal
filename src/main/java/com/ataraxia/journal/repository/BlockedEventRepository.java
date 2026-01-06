package com.ataraxia.journal.repository;

import com.ataraxia.journal.model.BlockedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlockedEventRepository extends JpaRepository<BlockedEvent, Long> {

    @Query("select b.ruleCode, count(b) from BlockedEvent b group by b.ruleCode order by count(b) desc")
    List<Object[]> countByRule();

    @Query("select b.strategy, count(b) from BlockedEvent b group by b.strategy order by count(b) desc")
    List<Object[]> countByStrategy();
}
