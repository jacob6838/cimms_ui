import { authApiHelper } from "./api-helper";
import ConfigParamsGeneral from "./fake_data/configParamsGeneral.json";
import configParamsIntersection from "./fake_data/configParamsIntersection.json";
import toast from "react-hot-toast";

class ConfigParamsApi {
  async getGeneralParameters(token: string): Promise<ConfigurationParameter[]> {
    return ConfigParamsGeneral;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/default/all",
        token: token,
      });
      return response as ConfigurationParameter[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getIntersectionParameters(
    token: string,
    intersectionId: string
  ): Promise<ConfigurationParameterIntersection[]> {
    return configParamsIntersection;
    try {
      var response = await authApiHelper.invokeApi({
        path: "/config/intersection/all",
        token: token,
        queryParams: { intersectionId },
      });
      return response as ConfigurationParameterIntersection[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getAllParameters(token: string, intersectionId: string): Promise<ConfigurationParameter[]> {
    const generalParams = await this.getGeneralParameters(token);
    const intersectionParams = await this.getIntersectionParameters(token, intersectionId);

    const allParams: ConfigurationParameter[] = [];

    for (const param of generalParams) {
      const intersectionParam = intersectionParams.find((p) => p.key === param.key);
      if (intersectionParam) {
        allParams.push(intersectionParam);
      } else {
        allParams.push(param);
      }
    }
    return allParams;
  }

  async getParameterGeneral(
    token: string,
    key: string,
    intersectionId?: string
  ): Promise<ConfigurationParameter | null> {
    return ConfigParamsGeneral.find((c) => c.key === key)!;
    try {
      var response = await authApiHelper.invokeApi({
        path: `/config/default/${key}`,
        token: token,
        failureMessage: "Failed to Retrieve Configuration Parameter " + name,
      });
      return response as ConfigurationParameter;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async getParameterIntersection(
    token: string,
    key: string,
    intersectionId?: string
  ): Promise<ConfigurationParameterIntersection | null> {
    return configParamsIntersection.find((c) => c.key === key)!;
    try {
      var response = await authApiHelper.invokeApi({
        path: `/config/intersection/${key}`,
        token: token,
        failureMessage: "Failed to Retrieve Configuration Parameter " + name,
      });
      return response as ConfigurationParameterIntersection;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async updateDefaultParameter(
    token: string,
    name: string,
    param: ConfigurationParameter
  ): Promise<ConfigurationParameter | null> {
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
      return response as ConfigurationParameter;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async updateIntersectionParameter(
    token: string,
    name: string,
    param: ConfigurationParameterIntersection
  ): Promise<ConfigurationParameterIntersection | null> {
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
      return response as ConfigurationParameterIntersection;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async createIntersectionParameter(
    token: string,
    name: string,
    value: ConfigurationParameter,
    intersectionID: number
  ): Promise<ConfigurationParameter | null> {
    toast.success(`Successfully Update Configuration Parameter ${name}`);
    // return null;

    const param: ConfigurationParameterIntersection = {
      intersectionID: intersectionID,
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
      return response as ConfigurationParameter;
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
  ): Promise<ConfigurationParameter | null> {
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
      return response as ConfigurationParameter;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }
}

export const configParamApi = new ConfigParamsApi();
