package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateConflictEvent;

public interface SignalStateConflictEventRepo extends MongoRepository<SignalStateConflictEvent, String> {    
    @Query("?0")
    List<SignalStateConflictEvent> query(String query);
}