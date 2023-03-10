package us.dot.its.jpo.ode.api.accessors.notifications.SignalGroupAlignmentNotificationRepo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalGroupAlignmentNotification;

public interface SignalGroupAlignmentNotificationRepository{
    Query getQuery(Integer intersectionID, Long startTime, Long endTime);

    long getQueryResultCount(Query query);
    
    List<SignalGroupAlignmentNotification> find(Query query);  
}