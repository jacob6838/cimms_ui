package us.dot.its.jpo.ode.api.accessors.spat;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalStateConflictNotification;
import us.dot.its.jpo.geojsonconverter.pojos.spat.ProcessedSpat;

public interface ProcessedSpatRepo extends MongoRepository<ProcessedSpat, String> {    
    @Query("{}")
    List<ProcessedSpat> query(String query);
}