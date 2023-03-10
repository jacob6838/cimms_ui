
    package us.dot.its.jpo.ode.api.accessors.events.SignalGroupAlignmentEvent;

    import java.util.List;

    import org.springframework.data.mongodb.core.query.Query;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalGroupAlignmentEvent;

    public interface SignalGroupAlignmentEventRepository{
        Query getQuery(Integer intersectionID, Long startTime, Long endTime);

        long getQueryResultCount(Query query);
        
        List<SignalGroupAlignmentEvent> find(Query query);  
    }

