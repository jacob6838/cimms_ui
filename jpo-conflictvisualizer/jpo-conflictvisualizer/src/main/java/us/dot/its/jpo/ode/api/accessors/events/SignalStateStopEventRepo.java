package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateStopEvent;

public interface SignalStateStopEventRepo extends MongoRepository<SignalStateStopEvent, String> {    
    @Query("?0")
    List<SignalStateStopEvent> query(String query);
}