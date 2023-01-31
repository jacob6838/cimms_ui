package us.dot.its.jpo.ode.api.controllers;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;
import us.dot.its.jpo.ode.mockdata.MockMapGenerator;

@RestController
public class MapController {

    private static final Logger logger = LoggerFactory.getLogger(MapController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    @RequestMapping(value = "/map/json", method = RequestMethod.GET, produces = "application/json")
	public List<ProcessedMap> findMaps(
            @RequestParam(name="Intersection ID", required = false) Integer intersectionID,
            @RequestParam(name="Start Time (UTC Millis)", required = false) Long startTime,
            @RequestParam(name="End Time (UTC Millis)", required = false) Long endTime,
            @RequestParam(name="Test Data", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<ProcessedMap> list = new ArrayList<>();
        
        if(testData){
            list = MockMapGenerator.getProcessedMaps();
        }else{

        }

        logger.debug(String.format("Returning %i results for MAP JSON Request.", list.size()));

		return list;
	}
}