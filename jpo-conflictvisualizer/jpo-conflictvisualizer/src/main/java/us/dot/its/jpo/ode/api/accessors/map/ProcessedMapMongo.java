package us.dot.its.jpo.ode.api.accessors.map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import us.dot.its.jpo.geojsonconverter.DateJsonMapper;
import us.dot.its.jpo.geojsonconverter.pojos.geojson.map.ProcessedMap;

@Document("ProcessedMap")
public class ProcessedMapMongo extends ProcessedMap{
    

    @Override
    public String toString() {
        ObjectMapper mapper = DateJsonMapper.getInstance();
        mapper.registerModule(new JavaTimeModule());
        String testReturn = "";
        try {
            testReturn = (mapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            System.out.println("ERROR\n\n");
            System.out.println(e.getMessage() +  e);
            System.out.println("End Error");
        }
        return testReturn;
    }
}
