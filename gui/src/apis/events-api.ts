import { number } from "prop-types";
import { authApiHelper } from "./api-helper";

class EventsApi {
  async getEvent(
    token: string,
    eventType: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<MessageMonitor.Event[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: `/events/${eventType}`,
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          latest: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/signal_state_stop
  async getSignalStateStop(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<SignalStateStopEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/signal_state_stop",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/signal_state_conflict
  async getSignalStateConflict(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<SignalStateConflictEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/signal_state_conflict",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/signal_state
  async getSignalState(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<SignalStateEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/signal_state",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/signal_group_alignment
  async getSignalGroupAlignment(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<SignalGroupAlignmentEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/signal_group_alignment",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/lane_direction_of_travel
  async getLaneDirectionOfTravel(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<LaneDirectionOfTravelEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/lane_direction_of_travel",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/intersection_reference_alignment
  async getIntersectionReferenceAlignmentEvent(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<IntersectionReferenceAlignmentEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/intersection_reference_alignment",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
      });
      console.log(response);
      return response;
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  // GET
  // /events/connection_of_travel
  async getTimeChangeDetails(
    token: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<TimeChangeDetailsEvent[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/events/time_change_details",
        token: token,
        queryParams: {
          intersection_id: intersectionId.toString(),
          start_time_utc_millis: startTime.getTime().toString(),
          end_time_utc_millis: endTime.getTime().toString(),
          test: "true",
        },
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
