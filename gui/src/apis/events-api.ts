import { number } from 'prop-types';
import { authApiHelper } from './api-helper';

class EventsApi {
    
    // GET
    
    
    // GET
    // /events/signal_state_stop
    
    // GET
    // /events/signal_state_conflict
    
    // GET
    // /events/signal_state
    
    // GET
    // /events/signal_group_alignment
    
    // GET
    // /events/lane_direction_of_travel
    
    // GET
    // /events/intersection_reference_alignment
    
    // GET
    // /events/connection_of_travel
    async getTimeChangeDetails(token: string, intersectionId: number, startTime: Date, endTime: Date) {
        try {
            var response = await authApiHelper.invokeApi({
              path: "/events/time_change_details",
              token: token,
              queryParams: {
                "Intersection ID": intersectionId.toString(),
                "Start Time (UTC Millis)": startTime.getTime().toString(),
                "End Time (UTC Millis)": endTime.getTime().toString(),
                "Test Data": "true",
              }
            });
            console.log(response);
            return response;
          } catch (exception_var) {
            console.error(exception_var);
            return [];
          }
    }
}

export default new EventsApi();