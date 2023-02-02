package us.dot.its.jpo.ode.api.accessors.notifications;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.broadcast_rate.SpatBroadcastRateNotification;

public interface SpatBroadcastRateNotificationRepo extends MongoRepository<SpatBroadcastRateNotification, String> {    
    @Query("?0")
    List<SpatBroadcastRateNotification> query(String query);
}