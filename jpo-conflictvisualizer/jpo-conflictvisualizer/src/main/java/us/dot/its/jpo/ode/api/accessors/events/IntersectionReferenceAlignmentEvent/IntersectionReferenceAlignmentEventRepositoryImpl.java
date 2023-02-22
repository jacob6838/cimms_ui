
    package us.dot.its.jpo.ode.api.accessors.events.IntersectionReferenceAlignmentEvent;

    import java.time.Instant;
    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.mongodb.core.MongoTemplate;
    import org.springframework.data.mongodb.core.query.Criteria;
    import org.springframework.data.mongodb.core.query.Query;
    import org.springframework.stereotype.Component;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.IntersectionReferenceAlignmentEvent;

    @Component
    public class IntersectionReferenceAlignmentEventRepositoryImpl implements IntersectionReferenceAlignmentEventRepository{
        
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
            return mongoTemplate.count(query, IntersectionReferenceAlignmentEvent.class, "CmIntersectionReferenceAlignmentEvent");
        }

        public List<IntersectionReferenceAlignmentEvent> find(Query query) {
            return mongoTemplate.find(query, IntersectionReferenceAlignmentEvent.class, "CmIntersectionReferenceAlignmentEvent");
        }

    }
