package us.dot.its.jpo.ode.api.accessors.config;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.config.IntersectionConfig;

public interface IntersectionConfigRepo<T> extends MongoRepository<IntersectionConfig<T>, String> {    
    @Query("?0")
    List<IntersectionConfig<T>> query(String query);


    @Query("{'key': ?0 'roadRegulatorID': ?1 'intersectionID': ?2 }")
    List<IntersectionConfig<T>> getIntersectionConfig(String key, String roadRegulatorID, int intersection);
}