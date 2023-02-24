
    package us.dot.its.jpo.ode.api.accessors.events.SignalStateStopEvent;

    import java.time.Instant;
    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.stereotype.Component;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateStopEvent;

    @Component
    public class SignalStateStopEventRepositoryImpl implements SignalStateStopEventRepository{
        
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
            return mongoTemplate.count(query, SignalStateStopEvent.class, "CmSignalStateStopEvent");
        }

        public List<SignalStateStopEvent> find(Query query) {
            return mongoTemplate.find(query, SignalStateStopEvent.class, "CmSignalStateStopEvent");
        }

    }
