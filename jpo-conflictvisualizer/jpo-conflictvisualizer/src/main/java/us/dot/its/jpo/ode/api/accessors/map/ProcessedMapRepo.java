package us.dot.its.jpo.ode.api.accessors.map;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;
import us.dot.its.jpo.ode.model.OdeBsmData;

public interface ProcessedMapRepo extends MongoRepository<ProcessedMapMongo, String> {

    @Query("{\"properties.intersectionId\": 12109}")
    List<ProcessedMapMongo> getProcessedMap(String intersectionID, String startTime, String endTime);

    
}