package us.dot.its.jpo.ode.api.accessors.notifications;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.IntersectionReferenceAlignmentNotification;

public interface IntersectionReferenceAlignmentNotificationRepo extends MongoRepository<IntersectionReferenceAlignmentNotification, String> {    
    @Query("?0")
    List<IntersectionReferenceAlignmentNotification> query(String query);
}