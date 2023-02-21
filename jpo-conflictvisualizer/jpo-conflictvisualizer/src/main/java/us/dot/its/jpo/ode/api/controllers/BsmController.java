package us.dot.its.jpo.ode.api.controllers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import us.dot.its.jpo.ode.api.accessors.bsm.OdeBsmJsonRepository;
import us.dot.its.jpo.ode.mockdata.MockBsmGenerator;
import us.dot.its.jpo.ode.model.OdeBsmData;

@RestController
public class BsmController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    OdeBsmJsonRepository odeBsmJsonRepo;

    public String getCurrentTime(){
        return ZonedDateTime.now().toInstant().toEpochMilli() + "";
    }

    
    @RequestMapping(value = "/bsm/json", method = RequestMethod.GET, produces = "application/json")
	public List<OdeBsmData> findBSMs(
            @RequestParam(name="origin_ip", required = false) String originIp,
            @RequestParam(name="start_time_utc_millis", required = false) Long startTime,
            @RequestParam(name="end_time_utc_millis", required = false) Long endTime,
            @RequestParam(name="test", required = false, defaultValue = "false") boolean testData
            ) {
        
        List<OdeBsmData> list = new ArrayList<>();
        
        if(testData){
            list = MockBsmGenerator.getJsonBsms();
        }else{
            list = odeBsmJsonRepo.findOdeBsmJson(originIp, startTime, endTime);
        }

        logger.debug(String.format("Returning %d results for BSM JSON Request.", list.size()));
		return list;
	}
}