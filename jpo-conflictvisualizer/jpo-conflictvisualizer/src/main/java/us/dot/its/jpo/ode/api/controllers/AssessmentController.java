package us.dot.its.jpo.ode.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.ConnectionOfTravelAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.LaneDirectionOfTravelAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateEventAssessment;
import us.dot.its.jpo.ode.api.accessors.assessments.ConnectionOfTravelAssessmentRepo;
import us.dot.its.jpo.ode.api.accessors.assessments.LaneDirectionOfTravelAssessmentRepo;
import us.dot.its.jpo.ode.api.accessors.assessments.SignalStateAssessmentRepo;
import us.dot.its.jpo.ode.api.accessors.assessments.SignalStateEventAssessmentRepo;
import us.dot.its.jpo.ode.api.query.EqualsCriteria;
import us.dot.its.jpo.ode.api.query.GreaterThanCriteria;
import us.dot.its.jpo.ode.api.query.LessThanCriteria;
import us.dot.its.jpo.ode.api.query.QueryBuilder;
import us.dot.its.jpo.ode.mockdata.MockAssessmentGenerator;


@RestController
public class AssessmentController {

    @Autowired
	LaneDirectionOfTravelAssessmentRepo laneDirectionOfTravelAssessmentRepo;

    @Autowired
	ConnectionOfTravelAssessmentRepo connectionOfTravelAssessmentRepo;

    @Autowired
    SignalStateAssessmentRepo signalStateAssessmentRepo;

    @Autowired
    SignalStateEventAssessmentRepo signalStateEventAssessmentRepo;

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }
    
    
    @RequestMapping(value = "/assessments/connection_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<ConnectionOfTravelAssessment> findConnectionOfTravelAssessment(
            @RequestParam(name="road_regulator_id", required = false) Integer roadRegulatorID,
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<ConnectionOfTravelAssessment> list = new ArrayList<>();
        
        if(testData){
            list.add(MockAssessmentGenerator.getConnectionOfTravelAssessment());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(intersectionID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", intersectionID));
            }

            if(roadRegulatorID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", roadRegulatorID));
            }

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("assessmentGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("assessmentGeneratedAt", startTime));
            }

            list = connectionOfTravelAssessmentRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Connection of Travel Assessment Request.", list.size()));
		return list;
	}

    
    @RequestMapping(value = "/assessments/lane_direction_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<LaneDirectionOfTravelAssessment> findLaneDirectionOfTravelAssessment(
            @RequestParam(name="road_regulator_id", required = false) Integer roadRegulatorID,
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
                List<LaneDirectionOfTravelAssessment> list = new ArrayList<>();
        
        if(testData){
            list.add(MockAssessmentGenerator.getLaneDirectionOfTravelAssessment());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(intersectionID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", intersectionID));
            }

            if(roadRegulatorID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", roadRegulatorID));
            }

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("assessmentGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("assessmentGeneratedAt", startTime));
            }

            list = laneDirectionOfTravelAssessmentRepo.query(builder.getQueryString());
            
        }

        logger.debug(String.format("Returning %d results for Lane Direction of Travel Assessment Request.", list.size()));

        return list;
		
	}

    
    @RequestMapping(value = "/assessments/signal_state_assessment", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateAssessment> findSignalStateAssessment(
            @RequestParam(name="road_regulator_id", required = false) Integer roadRegulatorID,
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<SignalStateAssessment> list = new ArrayList<>();
        
        if(testData){
            list.add(MockAssessmentGenerator.getSignalStateAssessment());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(intersectionID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", intersectionID));
            }

            if(roadRegulatorID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", roadRegulatorID));
            }

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("assessmentGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("assessmentGeneratedAt", startTime));
            }

            list = signalStateAssessmentRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Signal State Assessment Request.", list.size()));

		return list;
	}

    
    @RequestMapping(value = "/assessments/signal_state_event_assessment", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateEventAssessment> findSignalStateEventAssessment(
            @RequestParam(name="road_regulator_id", required = false) Integer roadRegulatorID,
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<SignalStateEventAssessment> list = new ArrayList<>();
        
        if(testData){
            list.add(MockAssessmentGenerator.getSignalStateEventAssessment());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(intersectionID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", intersectionID));
            }

            if(roadRegulatorID != null){
                builder.addCriteria(new EqualsCriteria<Integer>("intersectionID", roadRegulatorID));
            }

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("assessmentGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("assessmentGeneratedAt", startTime));
            }

            list = signalStateEventAssessmentRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Signal State Event Assessment Request.", list.size()));

		return list;
	}
}