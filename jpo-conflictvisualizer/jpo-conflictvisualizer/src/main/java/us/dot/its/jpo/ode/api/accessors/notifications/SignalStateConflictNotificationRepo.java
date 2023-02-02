package us.dot.its.jpo.ode.api.accessors.notifications;


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalStateConflictNotification;

public interface SignalStateConflictNotificationRepo extends MongoRepository<SignalStateConflictNotification, String> {    
    @Query("?0")
    List<SignalStateConflictNotification> query(String query);
}