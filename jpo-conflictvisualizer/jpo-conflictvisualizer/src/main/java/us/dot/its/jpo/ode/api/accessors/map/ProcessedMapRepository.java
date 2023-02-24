package us.dot.its.jpo.ode.api.accessors.map;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;

public interface ProcessedMapRepository{
    Query getQuery(Integer intersectionID, Long startTime, Long endTime);

    long getQueryResultCount(Query query);
    
    List<ProcessedMap> findProcessedMaps(Query query);  
}