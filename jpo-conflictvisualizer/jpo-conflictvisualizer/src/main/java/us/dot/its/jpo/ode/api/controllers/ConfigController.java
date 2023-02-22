package us.dot.its.jpo.ode.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import scala.collection.generic.BitOperations.Long;

import org.springframework.web.bind.annotation.RequestBody;

import us.dot.its.jpo.conflictmonitor.monitor.models.Intersection.Intersection;
import us.dot.its.jpo.conflictmonitor.monitor.models.config.Config;
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

    // General Setter for Default Configs
    @PostMapping(value = "/config/default/")
    public @ResponseBody ResponseEntity<String> default_config(@RequestBody DefaultConfig config) {
        try {
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    // General Setter for Intersection Configs
    @PostMapping(value = "/config/intersection/")
    public @ResponseBody ResponseEntity<String> intersection_config(@RequestBody IntersectionConfig config) {
        try {
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @DeleteMapping(value = "/config/intersection/")
    public @ResponseBody ResponseEntity<String> intersection_config_delete(@RequestBody IntersectionConfig config) {
        try {
            intersectionConfigRepo.deleteById(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    // Retrieve All Config Params for Intersection Configs
    @RequestMapping(value = "/config/default/all", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<DefaultConfig>> default_config_all() {
        List<DefaultConfig> list = defaultConfigRepo.getAll();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new ArrayList<DefaultConfig>());
        }
    }

    // Retrieve All Parameters for Unique Intersections
    @RequestMapping(value = "/config/intersection/all", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<IntersectionConfig>> intersection_config_all() {
        List<IntersectionConfig> list = intersectionConfigRepo.getAll();
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new ArrayList<IntersectionConfig>());
        }
    }

    @RequestMapping(value = "/config/intersection/unique", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<Config>> intersection_config_unique(
        @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
        @RequestParam(name = "intersection_id", required = true) int intersectionID
    ) {
        List<DefaultConfig> defaultList = defaultConfigRepo.getAll();
        List<IntersectionConfig> intersectionList = intersectionConfigRepo.getIntersectionUniqueConfig(roadRegulatorID, intersectionID);
        List<Config> finalConfig = new ArrayList<>();

        for (DefaultConfig defaultConfig : defaultList) {
            Config addConfig = defaultConfig;
            for (IntersectionConfig intersectionConfig : intersectionList) {
                if (intersectionConfig.getKey().equals(defaultConfig.getKey())) {
                    addConfig = intersectionConfig;
                    break;
                }
            }
            finalConfig.add(addConfig);
        }

        if (finalConfig.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(finalConfig);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new ArrayList<Config>());
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_lane_direction_of_travel_minimum_speed_threshold() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ldot_minimum_speed_threshold");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ldot_minimum_speed_threshold", "lane_direction_of_travel", null));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_lane_direction_of_travel_minimum_speed_threshold(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot_minimum_speed_threshold", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ldot_minimum_speed_threshold", "lane_direction_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_speed_threshold(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ldot_minimum_speed_threshold",
                    "lane_direction_of_travel", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_speed_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_speed_threshold(
            @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ldot_minimum_speed_threshold",
                    "lane_direction_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_lane_direction_of_travel_minimum_number_of_points() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ldot_minimum_number_of_points");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ldot_minimum_number_of_points", "lane_direction_of_travel",
                            null));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_lane_direction_of_travel_minimum_number_of_points(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot_minimum_number_of_points", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ldot_minimum_number_of_points", "lane_direction_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_points(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ldot_minimum_number_of_points",
                    "lane_direction_of_travel", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_points", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_points(
        @RequestBody IntersectionConfig newConfig        
    ) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ldot_minimum_number_of_points",
                    "lane_direction_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_lane_direction_of_travel_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("ldot_look_back_period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("ldot_look_back_period", "lane_direction_of_travel", null));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_lane_direction_of_travel_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot_look_back_period", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("ldot_look_back_period", "lane_direction_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_look_back_period(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("ldot_look_back_period",
                    "lane_direction_of_travel", (Long)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_look_back_period(
            @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("ldot_look_back_period",
                    "lane_direction_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Long)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_lane_direction_of_travel_heading_tolerance() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ldot_heading_tolerance");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ldot_heading_tolerance", "lane_direction_of_travel", null));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_lane_direction_of_travel_heading_tolerance(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot_heading_tolerance", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ldot_heading_tolerance", "lane_direction_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_heading_tolerance(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ldot_heading_tolerance",
                    "lane_direction_of_travel", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_heading_tolerance(
            @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ldot_heading_tolerance",
                    "lane_direction_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_lane_direction_of_travel_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ldot_minimum_number_of_events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ldot_minimum_number_of_events", "lane_direction_of_travel",
                            null));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_lane_direction_of_travel_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ldot_minimum_number_of_events", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ldot_minimum_number_of_events", "lane_direction_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_events(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ldot_minimum_number_of_events",
                    "lane_direction_of_travel", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/lane_direction_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_lane_direction_of_travel_minimum_number_of_events(
            @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ldot_minimum_number_of_events",
                    "lane_direction_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/maximum_distance_from_stopbar", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_maximum_distance_from_stopbar() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss_maximum_distance_from_stopbar");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss_maximum_distance_from_stopbar", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/maximum_distance_from_stopbar", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_maximum_distance_from_stopbar(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_maximum_distance_from_stopbar", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss_maximum_distance_from_stopbar", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/maximum_distance_from_stopbar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_maximum_distance_from_stopbar(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss_maximum_distance_from_stopbar",
                    "signal_state", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/maximum_distance_from_stopbar", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_maximum_distance_from_stopbar(
            @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss_maximum_distance_from_stopbar",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_heading_tolerance() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss_heading_tolerance");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss_heading_tolerance", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/heading_tolerance", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_heading_tolerance(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_heading_tolerance", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss_heading_tolerance", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_heading_tolerance(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss_heading_tolerance",
                    "signal_state", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/heading_tolerance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_heading_tolerance(@RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss_heading_tolerance",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_signal_state_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("ss_look_back_period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("ss_look_back_period", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_signal_state_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_look_back_period", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("ss_look_back_period", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_look_back_period(
            @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("ss_look_back_period",
                    "signal_state", (Long)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_look_back_period(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("ss_look_back_period",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Long)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_minimum_red_light_percentage_threshold() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss_minimum_red_light_percentage_threshold");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss_minimum_red_light_percentage_threshold", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_minimum_red_light_percentage_threshold(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_minimum_red_light_percentage_threshold", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss_minimum_red_light_percentage_threshold", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_red_light_percentage_threshold(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss_minimum_red_light_percentage_threshold",
                    "signal_state", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/minimum_red_light_percentage_threshold", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_red_light_percentage_threshold(@RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>(
                    "ss_minimum_red_light_percentage_threshold",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_signal_state_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("ss_minimum_number_of_events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("ss_minimum_number_of_events", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_signal_state_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_minimum_number_of_events", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("ss_minimum_number_of_events", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_number_of_events(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("ss_minimum_number_of_events",
                    "signal_state", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_minimum_number_of_events(@RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("ss_minimum_number_of_events",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/red_light_running_minimum_speed", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Double>> default_signal_state_red_light_running_minimum_speed() {
        List<DefaultConfig<Double>> list = defaultConfigRepo.getConfig("ss_red_light_running_minimum_speed");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Double>("ss_red_light_running_minimum_speed", "signal_state", null));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/red_light_running_minimum_speed", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Double>> intersection_signal_state_red_light_running_minimum_speed(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Double>> list = intersectionConfigRepo
                .getIntersectionConfig("ss_red_light_running_minimum_speed", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Double>("ss_red_light_running_minimum_speed", "signal_state",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/signal_state/red_light_running_minimum_speed", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_red_light_running_minimum_speed(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Double> config = new DefaultConfig<Double>("ss_red_light_running_minimum_speed",
                    "signal_state", (Double)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/signal_state/red_light_running_minimum_speed", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_signal_state_red_light_running_minimum_speed(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Double> config = new IntersectionConfig<Double>("ss_red_light_running_minimum_speed",
                    "signal_state", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Double)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/connection_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_connection_of_travel_look_back_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("cot_look_back_period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("cot_look_back_period", "connection_of_travel", null));
        }
    }

    @RequestMapping(value = "/config/intersection/connection_of_travel/look_back_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_connection_of_travel_look_back_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("cot_look_back_period", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("cot_look_back_period", "connection_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/connection_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_look_back_period(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("cot_look_back_period",
                    "connection_of_travel", (Long)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/connection_of_travel/look_back_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_look_back_period(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("cot_look_back_period",
                    "connection_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Long)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/connection_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_connection_of_travel_minimum_number_of_events() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("cot_minimum_number_of_events");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("cot_minimum_number_of_events", "connection_of_travel", null));
        }
    }

    @RequestMapping(value = "/config/intersection/connection_of_travel/minimum_number_of_events", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_connection_of_travel_minimum_number_of_events(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("cot_minimum_number_of_events", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("cot_minimum_number_of_events", "connection_of_travel",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/connection_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_minimum_number_of_events(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("cot_minimum_number_of_events",
                    "connection_of_travel", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/connection_of_travel/minimum_number_of_events", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_connection_of_travel_minimum_number_of_events(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("cot_minimum_number_of_events",
                    "connection_of_travel", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/v2x_message_processing_frequency", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_v2x_message_processing_frequency() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g_v2x_message_processing_frequency");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g_v2x_message_processing_frequency", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/v2x_message_processing_frequency", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_v2x_message_processing_frequency(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g_v2x_message_processing_frequency", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g_v2x_message_processing_frequency", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/v2x_message_processing_frequency", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_v2x_message_processing_frequency(@RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g_v2x_message_processing_frequency",
                    "general", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/v2x_message_processing_frequency", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_v2x_message_processing_frequency(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g_v2x_message_processing_frequency",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/message_storage_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Long>> default_general_message_storage_period() {
        List<DefaultConfig<Long>> list = defaultConfigRepo.getConfig("g_message_storage_period");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Long>("g_message_storage_period", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/message_storage_period", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Long>> intersection_general_message_storage_period(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Long>> list = intersectionConfigRepo
                .getIntersectionConfig("g_message_storage_period", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Long>("g_message_storage_period", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/message_storage_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_message_storage_period(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Long> config = new DefaultConfig<Long>("g_message_storage_period",
                    "general", (Long)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/message_storage_period", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_message_storage_period(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Long> config = new IntersectionConfig<Long>("g_message_storage_period",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Long)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/spat_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_spat_minimum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g_spat_minimum_10_second_reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g_spat_minimum_10_second_reception", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/spat_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_spat_minimum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g_spat_minimum_10_second_reception", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g_spat_minimum_10_second_reception", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/spat_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_minimum_10_second_reception(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g_spat_minimum_10_second_reception",
                    "general", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/spat_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_minimum_10_second_reception(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g_spat_minimum_10_second_reception",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/spat_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_spat_maximum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g_spat_maximum_10_second_reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g_spat_maximum_10_second_reception", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/spat_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_spat_maximum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g_spat_maximum_10_second_reception", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g_spat_maximum_10_second_reception", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/spat_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_maximum_10_second_reception(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g_spat_maximum_10_second_reception",
                    "general", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/spat_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_spat_maximum_10_second_reception(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g_spat_maximum_10_second_reception",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/map_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_map_minimum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g_map_minimum_10_second_reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g_map_minimum_10_second_reception", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/map_minimum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_map_minimum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g_map_minimum_10_second_reception", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g_map_minimum_10_second_reception", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/map_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_minimum_10_second_reception(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g_map_minimum_10_second_reception",
                    "general", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/map_minimum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_minimum_10_second_reception(@RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g_map_minimum_10_second_reception",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/default/general/map_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<DefaultConfig<Integer>> default_general_map_maximum_10_second_reception() {
        List<DefaultConfig<Integer>> list = defaultConfigRepo.getConfig("g_map_maximum_10_second_reception");
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new DefaultConfig<Integer>("g_map_maximum_10_second_reception", "general", null));
        }
    }

    @RequestMapping(value = "/config/intersection/general/map_maximum_10_second_reception", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<IntersectionConfig<Integer>> intersection_general_map_maximum_10_second_reception(
            @RequestParam(name = "road_regulator_id", required = true) String roadRegulatorID,
            @RequestParam(name = "intersection_id", required = true) int intersectionID) {
        List<IntersectionConfig<Integer>> list = intersectionConfigRepo
                .getIntersectionConfig("g_map_maximum_10_second_reception", roadRegulatorID, intersectionID);
        if (list.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(list.get(0));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new IntersectionConfig<Integer>("g_map_maximum_10_second_reception", "general",
                            roadRegulatorID, intersectionID, null, null));
        }
    }

    @RequestMapping(value = "/config/default/general/map_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_maximum_10_second_reception(
        @RequestBody DefaultConfig newConfig) {
        try {
            DefaultConfig<Integer> config = new DefaultConfig<Integer>("g_map_maximum_10_second_reception",
                    "general", (Integer)newConfig.getValue());
            defaultConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(value = "/config/intersection/general/map_maximum_10_second_reception", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseEntity<String> default_general_map_maximum_10_second_reception(
        @RequestBody IntersectionConfig newConfig) {
        try {
            String rsuID = "0";
            IntersectionConfig<Integer> config = new IntersectionConfig<Integer>("g_map_maximum_10_second_reception",
                    "general", newConfig.getRoadRegulatorID(), newConfig.getIntersectionID(), rsuID, (Integer)newConfig.getValue());
            intersectionConfigRepo.save(config);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(config.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN)
                    .body(ExceptionUtils.getStackTrace(e));
        }
    }
}