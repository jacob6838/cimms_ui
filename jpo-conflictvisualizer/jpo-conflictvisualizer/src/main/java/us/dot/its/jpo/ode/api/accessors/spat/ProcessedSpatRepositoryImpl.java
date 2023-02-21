package us.dot.its.jpo.ode.api.accessors.spat;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import us.dot.its.jpo.geojsonconverter.pojos.spat.ProcessedSpat;


@Component
public class ProcessedSpatRepositoryImpl implements ProcessedSpatRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ProcessedSpat> findProcessedSpats(Integer intersectionID, Long startTime, Long endTime) {
        Query query = new Query();

        if(intersectionID != null){
            query.addCriteria(Criteria.where("intersectionId").is(intersectionID));
        }

        String startTimeString = Instant.ofEpochMilli(0).toString();
        String endTimeString = Instant.now().toString();

        if(startTime != null){
            startTimeString = Instant.ofEpochMilli(startTime).toString(); 
        }
        if(endTime != null){
            endTimeString = Instant.ofEpochMilli(endTime).toString();
        }

        query.addCriteria(Criteria.where("utcTimeStamp").gte(startTimeString).lte(endTimeString));
        List<ProcessedSpat> documents = mongoTemplate.find(query, ProcessedSpat.class);
        return documents;
    }

}