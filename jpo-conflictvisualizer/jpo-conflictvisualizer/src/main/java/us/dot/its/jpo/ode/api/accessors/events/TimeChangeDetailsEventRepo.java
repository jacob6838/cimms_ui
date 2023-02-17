package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.TimeChangeDetailsEvent;

public interface TimeChangeDetailsEventRepo extends MongoRepository<TimeChangeDetailsEvent, String> {    
    @Query("?0")
    List<TimeChangeDetailsEvent> query(String query);
}