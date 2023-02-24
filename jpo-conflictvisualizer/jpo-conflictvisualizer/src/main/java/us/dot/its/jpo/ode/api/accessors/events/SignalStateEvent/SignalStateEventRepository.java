
    package us.dot.its.jpo.ode.api.accessors.events.SignalStateEvent;

    import java.util.List;

    import org.springframework.data.mongodb.core.query.Query;
    import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateEvent;

    public interface SignalStateEventRepository{
        Query getQuery(Integer intersectionID, Long startTime, Long endTime);

        long getQueryResultCount(Query query);
        
        List<SignalStateEvent> find(Query query);  
    }

