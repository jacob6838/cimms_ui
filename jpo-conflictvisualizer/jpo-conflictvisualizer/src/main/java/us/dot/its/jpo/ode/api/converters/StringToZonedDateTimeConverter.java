package us.dot.its.jpo.ode.api.converters;

import java.time.ZonedDateTime;
import org.springframework.core.convert.converter.Converter;

public class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(String source) {
        return ZonedDateTime.parse(source);
    }
}