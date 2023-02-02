package us.dot.its.jpo.ode.api.accessors.notifications;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalGroupAlignmentNotification;

public interface SignalGroupAlignmentNotificationRepo extends MongoRepository<SignalGroupAlignmentNotification, String> {    
    @Query("?0")
    List<SignalGroupAlignmentNotification> query(String query);
}