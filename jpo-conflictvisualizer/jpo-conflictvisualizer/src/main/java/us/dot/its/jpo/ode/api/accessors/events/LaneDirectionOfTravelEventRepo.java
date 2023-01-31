package us.dot.its.jpo.ode.api.accessors.events;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.LaneDirectionOfTravelEvent;

public interface LaneDirectionOfTravelEventRepo extends MongoRepository<LaneDirectionOfTravelEvent, String> {    
    @Query("?0")
    List<LaneDirectionOfTravelEvent> query(String query);
}