package us.dot.its.jpo.ode.api.accessors.notifications.SignalGroupAlignmentNotificationRepo;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalGroupAlignmentNotification;


@Component
public class SignalGroupAlignmentNotificationRepositoryImpl implements SignalGroupAlignmentNotificationRepository{
    
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
        return mongoTemplate.count(query, SignalGroupAlignmentNotification.class);
    }

    public List<SignalGroupAlignmentNotification> find(Query query) {
        return mongoTemplate.find(query, SignalGroupAlignmentNotification.class);
    }

}