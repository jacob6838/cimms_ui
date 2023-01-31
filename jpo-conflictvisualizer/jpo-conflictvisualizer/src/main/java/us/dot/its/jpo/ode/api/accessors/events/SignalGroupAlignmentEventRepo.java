package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalGroupAlignmentEvent;

public interface SignalGroupAlignmentEventRepo extends MongoRepository<SignalGroupAlignmentEvent, String> {    
    @Query("?0")
    List<SignalGroupAlignmentEvent> query(String query);
}