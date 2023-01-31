package us.dot.its.jpo.ode.api.accessors.assessments;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateAssessment;

public interface SignalStateAssessmentRepo extends MongoRepository<SignalStateAssessment, String> {    
    @Query("?0")
    List<SignalStateAssessment> query(String query);
}