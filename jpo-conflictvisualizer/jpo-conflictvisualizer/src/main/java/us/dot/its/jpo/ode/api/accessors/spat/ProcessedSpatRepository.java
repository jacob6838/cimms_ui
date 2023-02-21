package us.dot.its.jpo.ode.api.accessors.spat;

import java.util.List;

import us.dot.its.jpo.geojsonconverter.pojos.spat.ProcessedSpat;

public interface ProcessedSpatRepository{
    List<ProcessedSpat> findProcessedSpats(Integer intersectionID, Long startTime, Long endTime);  
}