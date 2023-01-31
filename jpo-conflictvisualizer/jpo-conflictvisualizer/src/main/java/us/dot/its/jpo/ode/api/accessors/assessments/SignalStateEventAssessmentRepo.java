package us.dot.its.jpo.ode.api.accessors.assessments;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateEventAssessment;

public interface SignalStateEventAssessmentRepo extends MongoRepository<SignalStateEventAssessment, String> {    
    @Query("?0")
    List<SignalStateEventAssessment> query(String query);
}