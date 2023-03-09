package us.dot.its.jpo.ode.api.accessors.notifications.SignalStateConflictNotification;


import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalStateConflictNotification;


@Component
public class SignalStateConflictNotificationRepositoryImpl implements SignalStateConflictNotificationRepository{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Query getQuery(Integer intersectionID, Long startTime, Long endTime){
        Query query = new Query();

        if(intersectionID != null){
            query.addCriteria(Criteria.where("event.intersectionID").is(intersectionID));
        }

        if(startTime == null){
            startTime = Instant.ofEpochMilli(0).toEpochMilli();
        }
        if(endTime == null){
            endTime = Instant.now().toEpochMilli(); 
        }

        query.addCriteria(Criteria.where("notificationGeneratedAt").gte(startTime).lte(endTime));
        return query;
    }

    public long getQueryResultCount(Query query){
        return mongoTemplate.count(query, SignalStateConflictNotification.class);
    }

    public List<SignalStateConflictNotification> find(Query query) {
        return mongoTemplate.find(query, SignalStateConflictNotification.class);
    }

}