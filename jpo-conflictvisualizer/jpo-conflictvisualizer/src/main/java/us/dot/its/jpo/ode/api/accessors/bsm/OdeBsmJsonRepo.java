package us.dot.its.jpo.ode.api.accessors.bsm;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.ode.model.OdeBsmData;

public interface OdeBsmJsonRepo extends MongoRepository<OdeBsmJsonMongo, String> {   

    @Query("'metadata.originIp': ?0, {'metadata.odeReceivedAt': {$gte: ?1, $lte: ?2}, 'payload.data.coreData.id': ?3}")
    List<OdeBsmJsonMongo> getOdeBsmJson(String originIP, String startTime, String endTime, String vehicleId);

    
}