package us.dot.its.jpo.ode.api.accessors.assessments;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.ConnectionOfTravelAssessment;

public interface ConnectionOfTravelAssessmentRepo extends MongoRepository<ConnectionOfTravelAssessment, String> {    
    @Query("?0")
    List<ConnectionOfTravelAssessment> query(String query);
}