package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.ConnectionOfTravelEvent;

public interface ConnectionOfTravelEventRepo extends MongoRepository<ConnectionOfTravelEvent, String> {    
    @Query("?0")
    List<ConnectionOfTravelEvent> query(String query);
}