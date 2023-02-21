package us.dot.its.jpo.ode.api.controllers;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import us.dot.its.jpo.geojsonconverter.DateJsonMapper;
import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;
import us.dot.its.jpo.ode.api.accessors.map.ProcessedMapRepository;
import us.dot.its.jpo.ode.api.accessors.map.ProcessedMapRepositoryImpl;
import us.dot.its.jpo.ode.mockdata.MockMapGenerator;

@RestController
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProcessedMapRepository processedMapRepo;

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    
    @RequestMapping(value = "/map/json", method = RequestMethod.GET, produces = "application/json")
	public List<ProcessedMap> findMaps(
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<ProcessedMap> list = new ArrayList<>();
        
        if(testData){
            list = MockMapGenerator.getProcessedMaps();
        }else{
            list = processedMapRepo.findProcessedMaps(intersectionID, startTime, endTime);
        }

        logger.debug(String.format("Returning %d results for MAP JSON Request.", list.size()));

		return list;
	}


    
}