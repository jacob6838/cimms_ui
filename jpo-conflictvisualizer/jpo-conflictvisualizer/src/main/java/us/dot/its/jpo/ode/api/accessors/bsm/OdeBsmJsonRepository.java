package us.dot.its.jpo.ode.api.accessors.bsm;

import java.util.List;

import us.dot.its.jpo.ode.model.OdeBsmData;

public interface OdeBsmJsonRepository{
    List<OdeBsmData> findOdeBsmJson(String originIp, Long startTime, Long endTime);  
}