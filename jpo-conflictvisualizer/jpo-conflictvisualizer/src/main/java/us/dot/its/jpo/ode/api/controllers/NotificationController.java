package us.dot.its.jpo.ode.api.controllers;


import org.springframework.web.bind.annotation.RestController;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.ConnectionOfTravelNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.IntersectionReferenceAlignmentNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.LaneDirectionOfTravelNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalGroupAlignmentNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.SignalStateConflictNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.broadcast_rate.MapBroadcastRateNotification;
import us.dot.its.jpo.conflictmonitor.monitor.models.notifications.broadcast_rate.SpatBroadcastRateNotification;
import us.dot.its.jpo.ode.api.accessors.notifications.ConnectionOfTravelNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.IntersectionReferenceAlignmentNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.LaneDirectionOfTravelNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.MapBroadcastRateNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.SignalGroupAlignmentNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.SignalStateConflictNotificationRepo;
import us.dot.its.jpo.ode.api.accessors.notifications.SpatBroadcastRateNotificationRepo;
import us.dot.its.jpo.ode.api.query.GreaterThanCriteria;
import us.dot.its.jpo.ode.api.query.LessThanCriteria;
import us.dot.its.jpo.ode.api.query.QueryBuilder;
import us.dot.its.jpo.ode.mockdata.MockNotificationGenerator;


@RestController
public class NotificationController {

    @Autowired
	ConnectionOfTravelNotificationRepo connectionOfTravelNotificationRepo;

    @Autowired
	IntersectionReferenceAlignmentNotificationRepo intersectionReferenceAlignmentNotificationRepo;

    @Autowired
	LaneDirectionOfTravelNotificationRepo laneDirectionOfTravelNotificationRepo;

    @Autowired
	MapBroadcastRateNotificationRepo mapBroadcastRateNotificationRepo;

    @Autowired
	SignalGroupAlignmentNotificationRepo signalGroupAlignmentNotificationRepo;

    @Autowired
	SignalStateConflictNotificationRepo signalStateConflictNotificationRepo;

    @Autowired
	SpatBroadcastRateNotificationRepo spatBroadcastRateNotificationRepo;

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/connection_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<ConnectionOfTravelNotification> findConnectionOfTravelNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<ConnectionOfTravelNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getConnectionOfTravelNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = connectionOfTravelNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Connection of Travel Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/intersection_reference_alignment", method = RequestMethod.GET, produces = "application/json")
	public List<IntersectionReferenceAlignmentNotification> findIntersectionReferenceAlignmentNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<IntersectionReferenceAlignmentNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getIntersectionReferenceAlignmentNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = intersectionReferenceAlignmentNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Intersection Reference Alignment Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/lane_direction_of_travel", method = RequestMethod.GET, produces = "application/json")
	public List<LaneDirectionOfTravelNotification> findLaneDirectionOfTravelNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<LaneDirectionOfTravelNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getLaneDirectionOfTravelNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = laneDirectionOfTravelNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Lane Direction of Travel Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/map_broadcast_rate_notification", method = RequestMethod.GET, produces = "application/json")
	public List<MapBroadcastRateNotification> findMapBroadcastRateNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<MapBroadcastRateNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getMapBroadcastRateNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = mapBroadcastRateNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Map Broadcast Rate Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/signal_group_alignment_notification", method = RequestMethod.GET, produces = "application/json")
	public List<SignalGroupAlignmentNotification> findSignalGroupAlignmentNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<SignalGroupAlignmentNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getSignalGroupAlignmentNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = signalGroupAlignmentNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Signal Group Alignment Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/signal_state_conflict_notification", method = RequestMethod.GET, produces = "application/json")
	public List<SignalStateConflictNotification> findSignalStateConflictNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<SignalStateConflictNotification> list = new ArrayList<>();
        
        if(testData){
            list.add(MockNotificationGenerator.getSignalStateConflictNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            list = signalStateConflictNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Signal State Conflict Notification Request.", list.size()));
		return list;
	}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/notifications/spat_broadcast_rate_notification", method = RequestMethod.GET, produces = "application/json")
	public List<SpatBroadcastRateNotification> findSpatBroadcastRateNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        
        List<SpatBroadcastRateNotification> list = new ArrayList<>();
        
        if(testData){
            System.out.println(MockNotificationGenerator.getSpatBroadcastRateNotification());

            list.add(MockNotificationGenerator.getSpatBroadcastRateNotification());
        }else{
            QueryBuilder builder = new QueryBuilder();

            if(endTime != null){
                builder.addCriteria(new LessThanCriteria<Long>("notificationGeneratedAt", endTime));
            }

            if(startTime != null){
                builder.addCriteria(new GreaterThanCriteria<Long>("notificationGeneratedAt", startTime));
            }

            System.out.println("Query String " + builder.getQueryString());

            list = spatBroadcastRateNotificationRepo.query(builder.getQueryString());
        }

        logger.debug(String.format("Returning %d results for Spat Broadcast Rate Notification Request.", list.size()));
		return list;
	}
}