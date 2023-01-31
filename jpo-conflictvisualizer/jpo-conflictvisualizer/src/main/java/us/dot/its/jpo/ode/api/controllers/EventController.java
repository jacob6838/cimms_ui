package us.dot.its.jpo.ode.api.controllers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.dot.its.jpo.conflictmonitor.monitor.models.events.ConnectionOfTravelEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.IntersectionReferenceAlignmentEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.LaneDirectionOfTravelEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalGroupAlignmentEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateConflictEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.SignalStateStopEvent;
import us.dot.its.jpo.conflictmonitor.monitor.models.events.TimeChangeDetailsEvent;
import us.dot.its.jpo.ode.api.accessors.events.LaneDirectionOfTravelEventRepo;
import us.dot.its.jpo.ode.mockdata.MockEventGenerator;

@RestController
public class EventController {

    @Autowired
	LaneDirectionOfTravelEventRepo laneDirectionOfTravelEventRepo;

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    @RequestMapping(value = "/events/intersection_reference_alignment", method = RequestMethod.GET, produces = "application/json")
	public List<IntersectionReferenceAlignmentEvent> findIntersectionReferenceAlignmentEvents(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<IntersectionReferenceAlignmentEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getIntersectionReferenceAlignmentEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Intersection Reference Alignment Event Request.", list.size()));
		return list;
	}

    @RequestMapping(value = "/events/connection_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<ConnectionOfTravelEvent> findConnectionOfTravelEvents(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<ConnectionOfTravelEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getConnectionOfTravelEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Connection of Travel Event Request.", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/lane_direction_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<LaneDirectionOfTravelEvent> findLaneDirectionOfTravelEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {

        
        
        List<LaneDirectionOfTravelEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getLaneDirectionOfTravelEvent());
        }else{
            list = laneDirectionOfTravelEventRepo.findAll();
            list.forEach(item -> System.out.println(item));
            System.out.println("Lane Direction Of Travel Events List: " + list);
        }

        logger.debug(String.format("Returning %i results for Lane Direction of Travel Event Request.", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/signal_group_alignment", method = RequestMethod.GET, produces = "application/json")
	public List<SignalGroupAlignmentEvent> findSignalGroupAlignmentEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<SignalGroupAlignmentEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getSignalGroupAlignmentEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Signal group Alignment Event Request.", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/signal_state_conflict", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateConflictEvent> findSignalStateConflictEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<SignalStateConflictEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getSignalStateConflictEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Signal state Conflict Event Request", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/signal_state", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateEvent> findSignalStateEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<SignalStateEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getSignalStateEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Signal State Event Request", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/signal_state_stop", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateStopEvent> findSignalStateStopEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<SignalStateStopEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getSignalStateStopEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Signal State Stop Event Request.", list.size()));

		return list;
	}

    @RequestMapping(value = "/events/time_change_details", method = RequestMethod.GET, produces = "application/json")
	public List<TimeChangeDetailsEvent> findTimeChangeDetailsEvent(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        ArrayList<TimeChangeDetailsEvent> list = new ArrayList<>();
        
        if(testData){
            list.add(MockEventGenerator.getTimeChangeDetailsEvent());
        }else{

        }

        logger.debug(String.format("Returning %i results for Time Change Details Request.", list.size()));

		return list;
	}
}
