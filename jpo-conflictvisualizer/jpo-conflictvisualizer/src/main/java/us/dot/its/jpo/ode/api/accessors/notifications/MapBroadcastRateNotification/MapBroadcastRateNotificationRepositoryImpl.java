package us.dot.its.jpo.ode.api.accessors.notifications.MapBroadcastRateNotification;


import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.broadcast_rate.MapBroadcastRateNotification;

@Component
public class MapBroadcastRateNotificationRepositoryImpl implements MapBroadcastRateNotificationRepository{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public Query getQuery(Integer intersectionID, Long startTime, Long endTime){
        Query query = new Query();

        if(intersectionID != null){
            query.addCriteria(Criteria.where("properties.intersectionId").is(intersectionID));
        }

        String startTimeString = Instant.ofEpochMilli(0).toString();
        String endTimeString = Instant.now().toString();

        if(startTime != null){
            startTimeString = Instant.ofEpochMilli(startTime).toString(); 
        }
        if(endTime != null){
            endTimeString = Instant.ofEpochMilli(endTime).toString();
        }

        query.addCriteria(Criteria.where("notificationGeneratedAt").gte(startTimeString).lte(endTimeString));
        return query;
    }

    public long getQueryResultCount(Query query){
        return mongoTemplate.count(query, MapBroadcastRateNotification.class);
    }

    public List<MapBroadcastRateNotification> find(Query query) {
        return mongoTemplate.find(query, MapBroadcastRateNotification.class);
    }

}