
    package us.dot.its.jpo.ode.api.accessors.assessments.LaneDirectionOfTravelAssessment;

    import java.util.List;

    import org.springframework.data.mongodb.core.query.Query;
    import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.LaneDirectionOfTravelAssessment;

    public interface LaneDirectionOfTravelAssessmentRepository{
        Query getQuery(Integer intersectionID, Long startTime, Long endTime);

        long getQueryResultCount(Query query);
        
        List<LaneDirectionOfTravelAssessment> find(Query query);  
    }

