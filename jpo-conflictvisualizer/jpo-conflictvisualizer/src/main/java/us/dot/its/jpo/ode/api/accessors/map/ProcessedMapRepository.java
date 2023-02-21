package us.dot.its.jpo.ode.api.accessors.map;

import java.util.List;
import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;

public interface ProcessedMapRepository{
    List<ProcessedMap> findProcessedMaps(Integer intersectionID, Long startTime, Long endTime);  
}