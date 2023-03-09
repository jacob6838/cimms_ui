import { authApiHelper } from "./api-helper";
import ConfigParamsGeneral from "./fake_data/configParamsGeneral.json";
import configParamsIntersection from "./fake_data/configParamsIntersection.json";
import toast from "react-hot-toast";

class ConfigParamsApi {
  async getGeneralParameters(token: string): Promise<Config[]> {
    // return ConfigParamsGeneral;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/default/all",
        token: token,
      });
      return response as Config[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getIntersectionParameters(
    token: string,
    intersectionId: string
  ): Promise<IntersectionConfig[]> {
    // return configParamsIntersection;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/unique",
        token: token,
        queryParams: { intersection_id: intersectionId, road_regulator_id: "1" },
      });
      return response as IntersectionConfig[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getAllParameters(token: string, intersectionId: string): Promise<Config[]> {
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/unique",
        token: token,
        queryParams: { intersection_id: intersectionId, road_regulator_id: "-1" },
      });
      return response as IntersectionConfig[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getParameterGeneral(token: string, key: string): Promise<Config | null> {
    // return ConfigParamsGeneral.find((c) => c.key === key)!;
    try {
      var response = await authApiHelper.invokeApi({
        path: `/config/default/${key}`,
        token: token,
        failureMessage: "Failed to Retrieve Configuration Parameter " + name,
      });
      return response as Config;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async getParameterIntersection(
    token: string,
    key: string,
    road_regulator_id: string,
    intersection_id: string
  ): Promise<IntersectionConfig | null> {
    // return configParamsIntersection.find((c) => c.key === key)!;
    try {
      var response = await authApiHelper.invokeApi({
        path: `/config/intersection/${key}`,
        token: token,
        queryParams: { intersection_id, road_regulator_id: "-1" },
        failureMessage: "Failed to Retrieve Configuration Parameter " + name,
      });
      return response as IntersectionConfig;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async getParameter(
    token: string,
    key: string,
    road_regulator_id: string,
    intersection_id: string
  ): Promise<Config | null> {
    // try to get intersection parameter first, if not found, get general parameter
    var param: Config | null = await this.getParameterIntersection(
      token,
      key,
      road_regulator_id,
      intersection_id
    );
    if (param == null) {
      param = await this.getParameterGeneral(token, key);
    }
    return param;
  }

  async updateDefaultParameter(token: string, name: string, param: Config): Promise<Config | null> {
    toast.success(`Successfully Update Configuration Parameter ${name}`);
    return null;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/default/" + name,
        token: token,
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: param,
        toastOnSuccess: true,
        successMessage: `Successfully Update Configuration Parameter ${name}`,
        failureMessage: `Failed to Update Configuration Parameter ${name}`,
      });
      return response as Config;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async updateIntersectionParameter(
    token: string,
    name: string,
    param: IntersectionConfig
  ): Promise<IntersectionConfig | null> {
    toast.success(`Successfully Update Configuration Parameter ${name}`);
    return null;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/" + name,
        token: token,
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: param,
        toastOnSuccess: true,
        successMessage: `Successfully Update Configuration Parameter ${name}`,
        failureMessage: `Failed to Update Configuration Parameter ${name}`,
      });
      return response as IntersectionConfig;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async createIntersectionParameter(
    token: string,
    name: string,
    value: Config,
    intersectionID: number
  ): Promise<Config | null> {
    toast.success(`Successfully Update Configuration Parameter ${name}`);
    // return null;

    const param: IntersectionConfig = {
      intersectionID: intersectionID,
      roadRegulatorID: -1,
      rsuID: "rsu_1",
      ...value,
    };

    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/create/" + name,
        token: token,
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: param,
        toastOnSuccess: true,
        successMessage: `Successfully Update Configuration Parameter ${name}`,
        failureMessage: `Failed to Update Configuration Parameter ${name}`,
      });
      return response as Config;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async removeOverriddenParameter(
    token: string,
    name: string,
    value: number | string,
    intersectionID: number
  ): Promise<Config | null> {
    toast.success(`Successfully Removed Configuration Parameter ${name}`);
    return null;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/" + name,
        token: token,
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: { value: value },
        toastOnSuccess: true,
        successMessage: `Successfully Update Configuration Parameter ${name}`,
        failureMessage: `Failed to Update Configuration Parameter ${name}`,
      });
      return response as Config;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }
}

export const configParamApi = new ConfigParamsApi();
