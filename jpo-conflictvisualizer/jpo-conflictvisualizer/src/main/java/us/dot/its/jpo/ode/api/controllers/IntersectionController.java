package us.dot.its.jpo.ode.api.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import us.dot.its.jpo.ode.api.IntersectionReferenceData;

@RestController
public class IntersectionController {
    

    @RequestMapping(value = "/intersection/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<IntersectionReferenceData>> getIntersections(
            @RequestParam(name = "test", required = false, defaultValue = "false") boolean testData) {

        if (testData) {
            IntersectionReferenceData ref = new IntersectionReferenceData();
            ref.setRsuIP("10.11.81.12");
            ref.setIntersectionID(12109);
            ref.setRoadRegulatorID("0");

            List<IntersectionReferenceData> refList = new ArrayList<>();
            refList.add(ref);

            return ResponseEntity.ok(refList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED,
                        "The requested endpoint is not yet implemented");
        }
    }
}
