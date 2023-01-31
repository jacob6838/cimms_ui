package us.dot.its.jpo.ode.api.accessors.assessments;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.LaneDirectionOfTravelAssessment;

public interface LaneDirectionOfTravelAssessmentRepo extends MongoRepository<LaneDirectionOfTravelAssessment, String> {    
    @Query("?0")
    List<LaneDirectionOfTravelAssessment> query(String query);
}