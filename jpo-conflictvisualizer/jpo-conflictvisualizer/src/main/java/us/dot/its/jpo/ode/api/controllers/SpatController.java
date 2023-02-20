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

import us.dot.its.jpo.geojsonconverter.pojos.spat.ProcessedSpat;
import us.dot.its.jpo.ode.api.accessors.spat.ProcessedSpatRepo;
import us.dot.its.jpo.ode.mockdata.MockSpatGenerator;

@RestController
public class SpatController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ProcessedSpatRepo processedSpatRepo;

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    
    @RequestMapping(value = "/spat/json", method = RequestMethod.GET, produces = "application/json")
	public List<ProcessedSpat> findSpats(
            @RequestParam(name="intersection_id", required = false) Integer intersectionID,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<ProcessedSpat> list = new ArrayList<>();
        
        if(testData){
            list = MockSpatGenerator.getProcessedSpats();
        }else{
            list = processedSpatRepo.query("{}");
            // processedSpatRepo.findById("63eab2b762b962385eb0064d");

        }

        logger.debug(String.format("Returning %d results for SPAT JSON Request.", list.size()));

		return list;
	}
}