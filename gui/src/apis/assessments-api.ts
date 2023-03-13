import { number } from "prop-types";
import { authApiHelper } from "./api-helper";

class AssessmentsApi {
  async getAssessment(
    token: string,
    eventType: string,
    intersectionId: number,
    startTime: Date,
    endTime: Date
  ): Promise<Assessment[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: `/assessments/${eventType}`,
        token: token,
        queryParams: {
          road_regulator_id: "-1",
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
}

export default new AssessmentsApi();
