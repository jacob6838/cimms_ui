
    package us.dot.its.jpo.ode.api.accessors.events.TimeChangeDetailsEvent;

    import java.time.Instant;
    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.stereotype.Component;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.TimeChangeDetailsEvent;

    @Component
    public class TimeChangeDetailsEventRepositoryImpl implements TimeChangeDetailsEventRepository{
        
        @Autowired
        private MongoTemplate mongoTemplate;

        public Query getQuery(Integer intersectionID, Long startTime, Long endTime){
            Query query = new Query();

            if(intersectionID != null){
                query.addCriteria(Criteria.where("intersectionID").is(intersectionID));
            }

            if(startTime == null){
                startTime = 0L; 
            }
            if(endTime == null){
                endTime = Instant.now().toEpochMilli();
            }

            query.addCriteria(Criteria.where("timestamp").gte(startTime).lte(endTime));
            return query;
        }

        public long getQueryResultCount(Query query){
            return mongoTemplate.count(query, TimeChangeDetailsEvent.class, "CmSpatTimeChangeDetailsEvent");
        }

        public List<TimeChangeDetailsEvent> find(Query query) {
            return mongoTemplate.find(query, TimeChangeDetailsEvent.class, "CmSpatTimeChangeDetailsEvent");
        }

    }
