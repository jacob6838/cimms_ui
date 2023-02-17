package us.dot.its.jpo.ode.api.accessors.config;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.config.DefaultConfig;

public interface DefaultConfigRepo<T> extends MongoRepository<DefaultConfig<T>, String> {    
    @Query("?0")
    List<DefaultConfig<T>> query(String query);

    @Query("{'key': ?0}")
    List<DefaultConfig<T>> getConfig(String key);
}