package us.dot.its.jpo.ode.api.accessors.bsm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import us.dot.its.jpo.geojsonconverter.DateJsonMapper;
import us.dot.its.jpo.ode.model.OdeBsmData;



@Getter
@Setter
@Document("OdeBsmJson")
public class OdeBsmJsonMongo extends OdeBsmData{

    // public String test;

    // public OdeBsmJsonMongo(){
    //     super();
    // }


    @Override
    public String toString() {
        // ObjectMapper mapper = DateJsonMapper.getInstance();
        // String testReturn = "";
        // try {
        //     testReturn = (mapper.writeValueAsString(this));
        // } catch (JsonProcessingException e) {
        //     System.out.println("ERROR\n\n");
        //     System.out.println(e.getMessage() +  e);
        //     System.out.println("End Error");
        // }
        return "Hello World!";
        // return testReturn;
    }

}
