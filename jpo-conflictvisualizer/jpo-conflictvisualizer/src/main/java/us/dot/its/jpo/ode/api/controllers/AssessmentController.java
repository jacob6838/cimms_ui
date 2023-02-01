package us.dot.its.jpo.ode.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.Assessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.ConnectionOfTravelAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.LaneDirectionOfTravelAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.assessments.SignalStateEventAssessment;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.Event;
import us.dot.its.jpo.ode.mockdata.MockAssessmentGenerator;

@RestController
public class AssessmentController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime() {
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/assessments/connection_of_travel", method = RequestMethod.GET, produces = "application/json")
    public List<ConnectionOfTravelAssessment> findConnectionOfTravelAssessment(
            @RequestParam(name = "Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name = "Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name = "End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name = "Test Data", required = false, defaultValue = "false") boolean testData) {

        ArrayList<ConnectionOfTravelAssessment> list = new ArrayList<>();

        if (testData) {
            list.add(MockAssessmentGenerator.getConnectionOfTravelAssessment());
        } else {

        }
        return list;
    }

    @RequestMapping(value = "/assessments/lane_direction_of_travel", method = RequestMethod.GET, produces = "application/json")
    public List<LaneDirectionOfTravelAssessment> findLaneDirectionOfTravelAssessment(
            @RequestParam(name = "Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name = "Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name = "End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name = "Test Data", required = false, defaultValue = "false") boolean testData) {

        ArrayList<LaneDirectionOfTravelAssessment> list = new ArrayList<>();

        if (testData) {
            list.add(MockAssessmentGenerator.getLaneDirectionOfTravelAssessment());
        } else {

        }
        return list;
    }

    @RequestMapping(value = "/assessments/signal_state_assessment", method = RequestMethod.GET, produces = "application/json")
    public List<SignalStateAssessment> findSignalStateAssessment(
            @RequestParam(name = "Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name = "Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name = "End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name = "Test Data", required = false, defaultValue = "false") boolean testData) {

        ArrayList<SignalStateAssessment> list = new ArrayList<>();

        if (testData) {
            list.add(MockAssessmentGenerator.getSignalStateAssessment());
        } else {

        }
        return list;
    }

    @RequestMapping(value = "/assessments/signal_state_event_assessment", method = RequestMethod.GET, produces = "application/json")
    public List<SignalStateEventAssessment> findSignalStateEventAssessment(
            @RequestParam(name = "Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name = "Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name = "End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name = "Test Data", required = false, defaultValue = "false") boolean testData) {

        ArrayList<SignalStateEventAssessment> list = new ArrayList<>();

        if (testData) {
            list.add(MockAssessmentGenerator.getSignalStateEventAssessment());
        } else {

        }
        return list;
    }

}