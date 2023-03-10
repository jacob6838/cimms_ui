package us.dot.its.jpo.ode.api.controllers;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import us.dot.its.jpo.ode.api.Properties;
import us.dot.its.jpo.ode.api.accessors.notifications.ConnectionOfTravelNotification.ConnectionOfTravelNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.IntersectionReferenceAlignmentNotification.IntersectionReferenceAlignmentNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.LaneDirectionOfTravelNotificationRepo.LaneDirectionOfTravelNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.MapBroadcastRateNotification.MapBroadcastRateNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.SignalGroupAlignmentNotificationRepo.SignalGroupAlignmentNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.SignalStateConflictNotification.SignalStateConflictNotificationRepository;
import us.dot.its.jpo.ode.api.accessors.notifications.SpatBroadcastRateNotification.SpatBroadcastRateNotificationRepository;
import us.dot.its.jpo.ode.mockdata.MockNotificationGenerator;


@RestController
public class NotificationController {

    @Autowired
	IntersectionReferenceAlignmentNotificationRepository intersectionReferenceAlignmentNotificationRepo;

    @Autowired
	LaneDirectionOfTravelNotificationRepository laneDirectionOfTravelNotificationRepo;

    @Autowired
	MapBroadcastRateNotificationRepository mapBroadcastRateNotificationRepo;

    @Autowired
	SignalGroupAlignmentNotificationRepository signalGroupAlignmentNotificationRepo;

    @Autowired
	SignalStateConflictNotificationRepository signalStateConflictNotificationRepo;

    @Autowired
	SpatBroadcastRateNotificationRepository spatBroadcastRateNotificationRepo;

    @Autowired
    ConnectionOfTravelNotificationRepository connectionOfTravelNotificationRepo;



    @Autowired
    Properties props;

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    
    @RequestMapping(value = "/notifications/connection_of_travel", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ConnectionOfTravelNotification>> findConnectionOfTravelNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        if(testData){
            List<ConnectionOfTravelNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getConnectionOfTravelNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = connectionOfTravelNotificationRepo.getQuery(null, startTime, endTime);
            long count = connectionOfTravelNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning ProcessedMap Response with Size: " + count);
                return ResponseEntity.ok(connectionOfTravelNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/intersection_reference_alignment", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<IntersectionReferenceAlignmentNotification>> findIntersectionReferenceAlignmentNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {

        if(testData){
            List<IntersectionReferenceAlignmentNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getIntersectionReferenceAlignmentNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = intersectionReferenceAlignmentNotificationRepo.getQuery(null, startTime, endTime);
            long count = intersectionReferenceAlignmentNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning IntersectionReferenceAlignmentNotification Response with Size: " + count);
                return ResponseEntity.ok(intersectionReferenceAlignmentNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/lane_direction_of_travel", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<LaneDirectionOfTravelNotification>> findLaneDirectionOfTravelNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {

        if(testData){
            List<LaneDirectionOfTravelNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getLaneDirectionOfTravelNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = laneDirectionOfTravelNotificationRepo.getQuery(null, startTime, endTime);
            long count = laneDirectionOfTravelNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning LaneDirectionOfTravelNotification Response with Size: " + count);
                return ResponseEntity.ok(laneDirectionOfTravelNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/map_broadcast_rate_notification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MapBroadcastRateNotification>> findMapBroadcastRateNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {

        if(testData){
            List<MapBroadcastRateNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getMapBroadcastRateNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = mapBroadcastRateNotificationRepo.getQuery(null, startTime, endTime);
            long count = mapBroadcastRateNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning MapBroadcastRateNotification Response with Size: " + count);
                return ResponseEntity.ok(mapBroadcastRateNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/signal_group_alignment_notification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SignalGroupAlignmentNotification>> findSignalGroupAlignmentNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        if(testData){
            List<SignalGroupAlignmentNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getSignalGroupAlignmentNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = signalGroupAlignmentNotificationRepo.getQuery(null, startTime, endTime);
            long count = signalGroupAlignmentNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning SignalGroupAlignmentNotification Response with Size: " + count);
                return ResponseEntity.ok(signalGroupAlignmentNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/signal_state_conflict_notification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SignalStateConflictNotification>> findSignalStateConflictNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {

        if(testData){
            List<SignalStateConflictNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getSignalStateConflictNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = signalStateConflictNotificationRepo.getQuery(null, startTime, endTime);
            long count = signalStateConflictNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning SignalGroupAlignmentNotification Response with Size: " + count);
                return ResponseEntity.ok(signalStateConflictNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}

    
    @RequestMapping(value = "/notifications/spat_broadcast_rate_notification", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<SpatBroadcastRateNotification>> findSpatBroadcastRateNotification(
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {

        if(testData){
            List<SpatBroadcastRateNotification> list = new ArrayList<>();
            list.add(MockNotificationGenerator.getSpatBroadcastRateNotification());
            return ResponseEntity.ok(list);
        }else{
            Query query = spatBroadcastRateNotificationRepo.getQuery(null, startTime, endTime);
            long count = spatBroadcastRateNotificationRepo.getQueryResultCount(query);
            if (count <= props.getMaximumResponseSize()) {
                logger.info("Returning SpatBroadcastRateNotification Response with Size: " + count);
                return ResponseEntity.ok(spatBroadcastRateNotificationRepo.find(query));
            } else {
                throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,
                        "The requested query has more results than allowed by server. Please reduce the query bounds and try again.");
            }
        }
	}
}