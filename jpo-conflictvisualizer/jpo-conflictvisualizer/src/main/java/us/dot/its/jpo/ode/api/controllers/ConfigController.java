package us.dot.its.jpo.ode.api.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import us.dot.its.jpo.conflictmonitor.monitor.models.config.DefaultConfig;
import us.dot.its.jpo.conflictmonitor.monitor.models.config.IntersectionConfig;
import us.dot.its.jpo.ode.api.accessors.config.DefaultConfigRepo;
import us.dot.its.jpo.ode.api.accessors.config.IntersectionConfigRepo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;

@RestController
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    DefaultConfigRepo defaultConfigRepo;

    @Autowired
    IntersectionConfigRepo intersectionConfigRepo;

    
    
    
    
    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_lane_direction_of_travel_minimum_speed_threshold() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ldot-minimum-speed-threshold");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ldot-minimum-speed-threshold", "lane-direction-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_lane_direction_of_travel_minimum_speed_threshold(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot-minimum-speed-threshold",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ldot-minimum-speed-threshold", "lane-direction-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_speed_threshold(
            @RequestParam(name = "lane_direction_of_travel", required = true) double minimumSpeedThreshold) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ldot-minimum-speed-threshold",
                    "lane-direction-of-travel", minimumSpeedThreshold);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_speed_threshold(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double minimumSpeedThreshold) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ldot-minimum-speed-threshold",
                    "lane-direction-of-travel", roadRegulatorId, intersectionID, rsuID, minimumSpeedThreshold);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_lane_direction_of_travel_minimum_number_of_points() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ldot-minimum-number-of-points");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ldot-minimum-number-of-points", "lane-direction-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_lane_direction_of_travel_minimum_number_of_points(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot-minimum-number-of-points",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ldot-minimum-number-of-points", "lane-direction-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_points(
            @RequestParam(name = "lane_direction_of_travel", required = true) int minimumNumberOfPoints) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ldot-minimum-number-of-points",
                    "lane-direction-of-travel", minimumNumberOfPoints);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_points(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int minimumNumberOfPoints) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ldot-minimum-number-of-points",
                    "lane-direction-of-travel", roadRegulatorId, intersectionID, rsuID, minimumNumberOfPoints);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_lane_direction_of_travel_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("ldot-look-back-period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("ldot-look-back-period", "lane-direction-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_lane_direction_of_travel_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot-look-back-period",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("ldot-look-back-period", "lane-direction-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_look_back_period(
            @RequestParam(name = "lane_direction_of_travel", required = true) long lookBackPeriod) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("ldot-look-back-period",
                    "lane-direction-of-travel", lookBackPeriod);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_look_back_period(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam long lookBackPeriod) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("ldot-look-back-period",
                    "lane-direction-of-travel", roadRegulatorId, intersectionID, rsuID, lookBackPeriod);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_lane_direction_of_travel_heading_tolerance() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ldot-heading-tolerance");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ldot-heading-tolerance", "lane-direction-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_lane_direction_of_travel_heading_tolerance(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot-heading-tolerance",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ldot-heading-tolerance", "lane-direction-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_heading_tolerance(
            @RequestParam(name = "lane_direction_of_travel", required = true) double headingTolerance) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ldot-heading-tolerance",
                    "lane-direction-of-travel", headingTolerance);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_heading_tolerance(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double headingTolerance) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ldot-heading-tolerance",
                    "lane-direction-of-travel", roadRegulatorId, intersectionID, rsuID, headingTolerance);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_lane_direction_of_travel_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ldot-minimum-number-of-events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ldot-minimum-number-of-events", "lane-direction-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_lane_direction_of_travel_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot-minimum-number-of-events",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ldot-minimum-number-of-events", "lane-direction-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_events(
            @RequestParam(name = "lane_direction_of_travel", required = true) int minimumNumberOfEvents) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ldot-minimum-number-of-events",
                    "lane-direction-of-travel", minimumNumberOfEvents);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_events(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int minimumNumberOfEvents) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ldot-minimum-number-of-events",
                    "lane-direction-of-travel", roadRegulatorId, intersectionID, rsuID, minimumNumberOfEvents);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/maximum_distance_from_stopbar", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_maximum_distance_from_stopbar() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss-maximum-distance-from-stopbar");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss-maximum-distance-from-stopbar", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/maximum_distance_from_stopbar", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_maximum_distance_from_stopbar(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-maximum-distance-from-stopbar",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss-maximum-distance-from-stopbar", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/maximum_distance_from_stopbar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_maximum_distance_from_stopbar(
            @RequestParam(name = "signal_state", required = true) double maximumDistanceFromStopbar) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss-maximum-distance-from-stopbar",
                    "signal-state", maximumDistanceFromStopbar);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/maximum_distance_from_stopbar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_maximum_distance_from_stopbar(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double maximumDistanceFromStopbar) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss-maximum-distance-from-stopbar",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, maximumDistanceFromStopbar);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_heading_tolerance() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss-heading-tolerance");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss-heading-tolerance", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_heading_tolerance(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-heading-tolerance",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss-heading-tolerance", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_heading_tolerance(
            @RequestParam(name = "signal_state", required = true) double headingTolerance) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss-heading-tolerance",
                    "signal-state", headingTolerance);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_heading_tolerance(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double headingTolerance) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss-heading-tolerance",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, headingTolerance);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_signal_state_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("ss-look-back-period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("ss-look-back-period", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_signal_state_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-look-back-period",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("ss-look-back-period", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_look_back_period(
            @RequestParam(name = "signal_state", required = true) long lookBackPeriod) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("ss-look-back-period",
                    "signal-state", lookBackPeriod);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_look_back_period(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam long lookBackPeriod) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("ss-look-back-period",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, lookBackPeriod);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_minimum_red_light_percentage_threshold() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss-minimum-red-light-percentage-threshold");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss-minimum-red-light-percentage-threshold", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_minimum_red_light_percentage_threshold(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-minimum-red-light-percentage-threshold",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss-minimum-red-light-percentage-threshold", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_red_light_percentage_threshold(
            @RequestParam(name = "signal_state", required = true) double minimumRedLightPercentageThreshold) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss-minimum-red-light-percentage-threshold",
                    "signal-state", minimumRedLightPercentageThreshold);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_red_light_percentage_threshold(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double minimumRedLightPercentageThreshold) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss-minimum-red-light-percentage-threshold",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, minimumRedLightPercentageThreshold);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_signal_state_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ss-minimum-number-of-events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ss-minimum-number-of-events", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_signal_state_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-minimum-number-of-events",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ss-minimum-number-of-events", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_number_of_events(
            @RequestParam(name = "signal_state", required = true) int minimumNumberOfEvents) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ss-minimum-number-of-events",
                    "signal-state", minimumNumberOfEvents);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_number_of_events(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int minimumNumberOfEvents) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ss-minimum-number-of-events",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, minimumNumberOfEvents);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/signal_state/red_light_running_minimum_speed", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_red_light_running_minimum_speed() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss-red-light-running-minimum-speed");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss-red-light-running-minimum-speed", "signal-state", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/red_light_running_minimum_speed", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_red_light_running_minimum_speed(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss-red-light-running-minimum-speed",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss-red-light-running-minimum-speed", "signal-state",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/signal_state/red_light_running_minimum_speed", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_red_light_running_minimum_speed(
            @RequestParam(name = "signal_state", required = true) double redLightRunningMinimumSpeed) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss-red-light-running-minimum-speed",
                    "signal-state", redLightRunningMinimumSpeed);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/signal_state/red_light_running_minimum_speed", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_red_light_running_minimum_speed(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam double redLightRunningMinimumSpeed) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss-red-light-running-minimum-speed",
                    "signal-state", roadRegulatorId, intersectionID, rsuID, redLightRunningMinimumSpeed);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/connection_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_connection_of_travel_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("cot-look-back-period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("cot-look-back-period", "connection-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/connection_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_connection_of_travel_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("cot-look-back-period",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("cot-look-back-period", "connection-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/connection_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_look_back_period(
            @RequestParam(name = "connection_of_travel", required = true) long lookBackPeriod) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("cot-look-back-period",
                    "connection-of-travel", lookBackPeriod);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/connection_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_look_back_period(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam long lookBackPeriod) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("cot-look-back-period",
                    "connection-of-travel", roadRegulatorId, intersectionID, rsuID, lookBackPeriod);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/connection_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_connection_of_travel_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("cot-minimum-number-of-events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("cot-minimum-number-of-events", "connection-of-travel", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/connection_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_connection_of_travel_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("cot-minimum-number-of-events",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("cot-minimum-number-of-events", "connection-of-travel",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/connection_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_minimum_number_of_events(
            @RequestParam(name = "connection_of_travel", required = true) int minimumNumberOfEvents) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("cot-minimum-number-of-events",
                    "connection-of-travel", minimumNumberOfEvents);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/connection_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_minimum_number_of_events(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int minimumNumberOfEvents) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("cot-minimum-number-of-events",
                    "connection-of-travel", roadRegulatorId, intersectionID, rsuID, minimumNumberOfEvents);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/v2x_message_processing_frequency", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_v2x_message_processing_frequency() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g-v2x-message-processing-frequency");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g-v2x-message-processing-frequency", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/v2x_message_processing_frequency", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_v2x_message_processing_frequency(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g-v2x-message-processing-frequency",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g-v2x-message-processing-frequency", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/v2x_message_processing_frequency", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_v2x_message_processing_frequency(
            @RequestParam(name = "general", required = true) int v2xMessageProcessingFrequency) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g-v2x-message-processing-frequency",
                    "general", v2xMessageProcessingFrequency);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/v2x_message_processing_frequency", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_v2x_message_processing_frequency(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int v2xMessageProcessingFrequency) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g-v2x-message-processing-frequency",
                    "general", roadRegulatorId, intersectionID, rsuID, v2xMessageProcessingFrequency);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/message_storage_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_general_message_storage_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("g-message-storage-period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("g-message-storage-period", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/message_storage_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_general_message_storage_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("g-message-storage-period",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("g-message-storage-period", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/message_storage_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_message_storage_period(
            @RequestParam(name = "general", required = true) long messageStoragePeriod) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("g-message-storage-period",
                    "general", messageStoragePeriod);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/message_storage_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_message_storage_period(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam long messageStoragePeriod) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("g-message-storage-period",
                    "general", roadRegulatorId, intersectionID, rsuID, messageStoragePeriod);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/spat_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_spat_minimum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g-spat-minimum-10-second-reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g-spat-minimum-10-second-reception", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/spat_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_spat_minimum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g-spat-minimum-10-second-reception",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g-spat-minimum-10-second-reception", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/spat_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_minimum_10_second_reception(
            @RequestParam(name = "general", required = true) int spatMinimum10SecondReception) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g-spat-minimum-10-second-reception",
                    "general", spatMinimum10SecondReception);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/spat_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_minimum_10_second_reception(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int spatMinimum10SecondReception) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g-spat-minimum-10-second-reception",
                    "general", roadRegulatorId, intersectionID, rsuID, spatMinimum10SecondReception);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/spat_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_spat_maximum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g-spat-maximum-10-second-reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g-spat-maximum-10-second-reception", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/spat_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_spat_maximum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g-spat-maximum-10-second-reception",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g-spat-maximum-10-second-reception", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/spat_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_maximum_10_second_reception(
            @RequestParam(name = "general", required = true) int spatMaximum10SecondReception) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g-spat-maximum-10-second-reception",
                    "general", spatMaximum10SecondReception);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/spat_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_maximum_10_second_reception(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int spatMaximum10SecondReception) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g-spat-maximum-10-second-reception",
                    "general", roadRegulatorId, intersectionID, rsuID, spatMaximum10SecondReception);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/map_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_map_minimum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g-map-minimum-10-second-reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g-map-minimum-10-second-reception", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/map_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_map_minimum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g-map-minimum-10-second-reception",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g-map-minimum-10-second-reception", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/map_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_minimum_10_second_reception(
            @RequestParam(name = "general", required = true) int mapMinimum10SecondReception) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g-map-minimum-10-second-reception",
                    "general", mapMinimum10SecondReception);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/map_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_minimum_10_second_reception(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int mapMinimum10SecondReception) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g-map-minimum-10-second-reception",
                    "general", roadRegulatorId, intersectionID, rsuID, mapMinimum10SecondReception);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }



    
    @RequestMapping(value = "/config/default/general/map_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_map_maximum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g-map-maximum-10-second-reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g-map-maximum-10-second-reception", "general", null));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/map_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_map_maximum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g-map-maximum-10-second-reception",roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g-map-maximum-10-second-reception", "general",
                            roadRegulatorID, intersectionID,null, null));
        }
    }

    
    @RequestMapping(value = "/config/default/general/map_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_maximum_10_second_reception(
            @RequestParam(name = "general", required = true) int mapMaximum10SecondReception) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g-map-maximum-10-second-reception",
                    "general", mapMaximum10SecondReception);
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    
    @RequestMapping(value = "/config/intersection/general/map_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_maximum_10_second_reception(
            @RequestParam int intersectionID, @RequestParam String roadRegulatorId, @RequestParam int mapMaximum10SecondReception) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g-map-maximum-10-second-reception",
                    "general", roadRegulatorId, intersectionID, rsuID, mapMaximum10SecondReception);
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }













    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_speed_threshold", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<DefaultConfig<Double>>
    // default_lane_direction_of_travel_minimum_speed_threshold() {
    // List<DefaultConfig<Double>> list =
    // defaultConfigRepo.getConfig("ldot-minimum-speed-threshold");
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // DefaultConfig<Double>("ldot-minimum-speed-threshold",
    // "lane-direction-of-travel", null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_speed_threshold",
    // method = RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<IntersectionConfig<Double>>
    // intersection_lane_direction_of_travel_minimum_speed_threshold(@RequestParam(name="intersection_id",
    // required = true) int intersectionID) {
    // List<IntersectionConfig<Double>> list =
    // intersectionConfigRepo.getIntersectionConfig("ldot-minimum-speed-threshold",
    // intersectionID);
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // IntersectionConfig<Double>("ldot-minimum-speed-threshold",
    // "lane-direction-of-travel", intersectionID, null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_speed_threshold", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_minimum_speed_threshold(@RequestParam(name="minimumSpeedThreshold",
    // required = true) double minimumSpeedThreshold) {
    // try {
    // DefaultConfig<Double> config = new
    // DefaultConfig<Double>("ldot-minimum-speed-threshold",
    // "lane-direction-of-travel", minimumSpeedThreshold);
    // defaultConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_speed_threshold",
    // method = RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_minimum_number_of_points(@RequestParam int
    // intersectionID, @RequestParam double minimumSpeedThreshold) {
    // try {
    // IntersectionConfig<Double> config = new
    // IntersectionConfig<Double>("ldot-minimum-speed-threshold",
    // "lane-direction-of-travel", intersectionID, minimumSpeedThreshold);
    // intersectionConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_number_of_points", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<DefaultConfig<Integer>>
    // default_lane_direction_of_travel_minimum_number_of_points() {
    // List<DefaultConfig<Integer>> list =
    // defaultConfigRepo.getConfig("ldot-minimum-number-of-points");
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // DefaultConfig<Integer>("ldot-minimum-number-of-points",
    // "lane-direction-of-travel", null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_number_of_points",
    // method = RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<IntersectionConfig<Integer>>
    // intersection_lane_direction_of_travel_minimum_number_of_points(@RequestParam(name="intersection_id",
    // required = true) int intersectionID) {
    // List<IntersectionConfig<Integer>> list =
    // intersectionConfigRepo.getIntersectionConfig("ldot-minimum-number-of-points",
    // intersectionID);
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // IntersectionConfig<Integer>("ldot-minimum-number-of-points",
    // "lane-direction-of-travel", intersectionID, null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_number_of_points", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_minimum_number_of_points(@RequestParam int
    // minimumNumberOfPoints) {
    // try {
    // DefaultConfig<Integer> config = new
    // DefaultConfig<Integer>("ldot-minimum-number-of-points",
    // "lane-direction-of-travel", minimumNumberOfPoints);
    // defaultConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_number_of_points",
    // method = RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // intersection_lane_direction_of_travel_minimum_number_of_points(@RequestParam
    // int intersectionID, @RequestParam Double minimumNumberOfPoints) {
    // try {
    // IntersectionConfig<Double> config = new
    // IntersectionConfig<Double>("ldot-minimum-number-of-points",
    // "lane-direction-of-travel", intersectionID, minimumNumberOfPoints);
    // intersectionConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/look_back_period", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<DefaultConfig<Integer>>
    // default_lane_direction_of_travel_look_back_period() {
    // List<DefaultConfig<Integer>> list =
    // defaultConfigRepo.getConfig("ldot-look-back-period");
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // DefaultConfig<Integer>("ldot-look-back-period", "lane-direction-of-travel",
    // null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/look_back_period", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<IntersectionConfig<Integer>>
    // intersection_lane_direction_of_travel_travel_look_back_period(@RequestParam(name="intersection_id",
    // required = true) int intersectionID) {
    // List<IntersectionConfig<Integer>> list =
    // intersectionConfigRepo.getIntersectionConfig("ldot-look-back-period",
    // intersectionID);
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // IntersectionConfig<Integer>("ldot-look-back-period",
    // "lane-direction-of-travel", intersectionID, null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/look_back_period", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_look_back_period(@RequestParam int
    // minimumNumberOfPoints) {
    // try {
    // DefaultConfig<Integer> config = new
    // DefaultConfig<Integer>("ldot-look-back-period", "lane-direction-of-travel",
    // minimumNumberOfPoints);
    // defaultConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/look_back_period", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // intersection_lane_direction_of_travel_look_back_period(@RequestParam int
    // intersectionID, @RequestParam int minimumNumberOfPoints) {
    // try {
    // IntersectionConfig<Integer> config = new
    // IntersectionConfig<Integer>("ldot-look-back-period",
    // "lane-direction-of-travel",intersectionID, minimumNumberOfPoints);
    // intersectionConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/heading_tolerance", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<DefaultConfig<Integer>>
    // default_lane_direction_of_travel_heading_tolerance() {
    // List<DefaultConfig<Integer>> list =
    // defaultConfigRepo.getConfig("ldot-heading-tolerance");
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // DefaultConfig<Integer>("ldot-heading-tolerance", "lane-direction-of-travel",
    // null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/heading_tolerance", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<IntersectionConfig<Integer>>
    // intersection_lane_direction_of_travel_travel_heading_tolerance(@RequestParam(name="intersection_id",
    // required = true) int intersectionID) {
    // List<IntersectionConfig<Integer>> list =
    // intersectionConfigRepo.getIntersectionConfig("ldot-heading-tolerance",
    // intersectionID);
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // IntersectionConfig<Integer>("ldot-look-back-period",
    // "lane-direction-of-travel", intersectionID, null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/heading_tolerance", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_heading_tolerance(@RequestParam double
    // headingTolerance) {
    // try {
    // DefaultConfig<Double> config = new
    // DefaultConfig<Double>("ldot-heading-tolerance", "lane-direction-of-travel",
    // headingTolerance);
    // defaultConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/heading_tolerance", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // intersection_lane_direction_of_travel_heading_tolerance(@RequestParam double
    // headingTolerance, @RequestParam int intersectionID) {
    // try {
    // IntersectionConfig<Double> config = new
    // IntersectionConfig<Double>("ldot-heading-tolerance",
    // "lane-direction-of-travel", intersectionID, headingTolerance);
    // intersectionConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_number_of_events", method =
    // RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<DefaultConfig<Integer>>
    // default_lane_direction_of_travel_minimum_number_of_events() {
    // List<DefaultConfig<Integer>> list =
    // defaultConfigRepo.getConfig("ldot-heading-tolerance");
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // DefaultConfig<Integer>("ldot-minimum-number-of-events",
    // "lane-direction-of-travel", null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_number_of_events",
    // method = RequestMethod.GET, produces = "application/json")
    // public @ResponseBody ResponseEntity<IntersectionConfig<Integer>>
    // intersection_lane_direction_of_travel_travel_minimum_number_of_events(@RequestParam(name="intersection_id",
    // required = true) int intersectionID) {
    // List<IntersectionConfig<Integer>> list =
    // intersectionConfigRepo.getIntersectionConfig("ldot-heading-tolerance",
    // intersectionID);
    // if(list.size() > 0){
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
    // }else{
    // return
    // ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new
    // IntersectionConfig<Integer>("ldot-minimum-number-of-events",
    // "lane-direction-of-travel", intersectionID, null));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/default/lane_direction_of_travel/minimum_number_of_events", method =
    // RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_minimum_number_of_events(@RequestParam int
    // minimumNumberOfEvents) {
    // try {
    // DefaultConfig<Integer> config = new
    // DefaultConfig<Integer>("ldot-minimum-number-of-events",
    // "lane-direction-of-travel", minimumNumberOfEvents);
    // defaultConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }

    // 
    // @RequestMapping(value =
    // "/config/intersection/lane_direction_of_travel/minimum_number_of_events",
    // method = RequestMethod.POST, produces = "application/json")
    // public @ResponseBody ResponseEntity<String>
    // default_lane_direction_of_travel_minimum_number_of_events(@RequestParam int
    // minimumNumberOfEvents, @RequestParam int intersectionID) {
    // try {
    // IntersectionConfig<Integer> config = new
    // IntersectionConfig<Integer>("ldot-minimum-number-of-events",
    // "lane-direction-of-travel", intersectionID, minimumNumberOfEvents);
    // intersectionConfigRepo.save(config);
    // return
    // ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
    // } catch (Exception e) {
    // return
    // ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
    // .body(ExceptionUtils.getStackTrace(e));
    // }
    // }
}
