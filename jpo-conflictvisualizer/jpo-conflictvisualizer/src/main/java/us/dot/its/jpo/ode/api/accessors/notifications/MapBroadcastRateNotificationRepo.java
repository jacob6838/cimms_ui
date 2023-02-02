package us.dot.its.jpo.ode.api.accessors.notifications;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.broadcast_rate.MapBroadcastRateNotification;

public interface MapBroadcastRateNotificationRepo extends MongoRepository<MapBroadcastRateNotification, String> {    
    @Query("?0")
    List<MapBroadcastRateNotification> query(String query);
}