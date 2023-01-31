import { authApigHelper } from './api-helper';
import ConfigParams from './fake_data/configParams.json';
import toast from 'react-hot-toast';


class ConfigParamsApi {

  async getParameters(token: string): Promise<ConfigurationParameter[]> {
    return ConfigParams;
    try {
      var response = await authApigHelper.invokeApi({
        path: "/config",
        token: token,
      });
      return response as ConfigurationParameter[];
    } catch (exception_var) {
      console.error(exception_var);
      return [];
    }
  }

  async getParameter(token: string, name: string): Promise<ConfigurationParameter | null> {
    return ConfigParams.find(c => c.name === name)!;
    try {
      var response = await authApigHelper.invokeApi({
        path: `/config/${name}`,
        token: token,
        failureMessage: "Failed to Retrieve Configuration Parameter " + name,
      });
      return response as ConfigurationParameter;
    } catch (exception_var) {
      console.error(exception_var);
      return null;
    }
  }

  async updateParameter(token: string, name: string, value: number | string): Promise<ConfigurationParameter | null> {
    toast.success(`Successfully Update Configuration Parameter ${name}`);
    return null;
    try {
      var response = await authApigHelper.invokeApi({
        path: "/config/" + name,
        token: token,
        method: "PUT",
        headers: { 'Content-Type': 'application/json' },
        body: { "value": value },
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
