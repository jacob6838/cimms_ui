package us.dot.its.jpo.ode.api;

import lombok.Data;

@Data
public class TimestampedCoordinate {
    long timestamp;
    double[] coords;
    float heading;
    float speed;
}
