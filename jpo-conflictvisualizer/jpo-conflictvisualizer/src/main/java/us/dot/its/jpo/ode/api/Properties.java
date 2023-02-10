package us.dot.its.jpo.ode.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;


// @ConfigurationProperties
@Configuration
// @PropertySource("classpath:application.properties")
@ConfigurationProperties()
public class Properties {

    private int maximumResponseSize;


    public int getMaximumResponseSize() {
        return maximumResponseSize;
    }

    @Value("${maximumResponseSize}")
    public void setMaximumResponseSize(int maximumResponseSize) {
        this.maximumResponseSize = maximumResponseSize;
    }
}
