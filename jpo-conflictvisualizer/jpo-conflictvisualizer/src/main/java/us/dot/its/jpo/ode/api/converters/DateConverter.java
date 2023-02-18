package us.dot.its.jpo.ode.api.converters;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.JavaType;
// import com.fasterxml.jackson.databind.type.TypeFactory;
// import com.fasterxml.jackson.databind.util.Converter;

@Component
@ConfigurationPropertiesBinding
public class DateConverter implements Converter<String, ZonedDateTime> {
  @Override
  public ZonedDateTime convert(String source) {
      if(source==null){
          return null;
      }
      System.out.println("Converting date: " + source);
      return ZonedDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
  }
}