
    package us.dot.its.jpo.ode.api.accessors.events.SignalStateStopEvent;

    import java.util.List;

    import org.springframework.data.mongodb.core.query.Query;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateStopEvent;

    public interface SignalStateStopEventRepository{
        Query getQuery(Integer intersectionID, Long startTime, Long endTime);

        long getQueryResultCount(Query query);
        
        List<SignalStateStopEvent> find(Query query);  
    }

