import toast from 'react-hot-toast';

const MESSAGE_MONITOR_ENDPOINT = "http://localhost:8081" //process.env.MESSAGE_MONITOR_API_ENDPOINT;

class AuthApiHelper {
  formatQueryParams(query_params: { [key: string]: string }) {
    if (!query_params) return;
    const params: string[] = [];
    for (const key in query_params) {
      if (query_params[key] !== "" && query_params[key] !== null) {
        params.push(`${key}=${query_params[key].replace(" ", "%20")}`);
      }
    }
    return !query_params ? "" : "?" + params.join("&");
  }

  async invokeApi({
    path,
    method = "GET",
    headers = {},
    queryParams = {},
    body = {},
    token,
    toastOnFailure = true,
    toastOnSuccess = false,
    successMessage = "Successfully completed request!",
    failureMessage = "Request failed to complete",
  }: {
    path: string,
    method?: string,
    headers?: { [key: string]: string },
    queryParams?: { [key: string]: string },
    body?: Object,
    token: string,
    toastOnFailure?: boolean,
    toastOnSuccess?: boolean,
    successMessage?: string,
    failureMessage?: string,
  }) {
    const url = MESSAGE_MONITOR_ENDPOINT! + path + this.formatQueryParams(queryParams)
        console.log("MAKING REQUEST TO", url)
        const results = method === "GET" ? await fetch(url, {
            method,
            headers,
          }) : await fetch(url, {
            method,
            headers,
            body: JSON.stringify(body),
          });

          const msg = await results.json();
          if (results.status !== 200 && results.status !== 202) {
            if (toastOnFailure) toast.error(failureMessage);
            console.error("Request failed with status code " + results.status + ": " + msg);
          } else if (msg?.code >= 400 && msg?.code < 500) {
            if (toastOnFailure) toast.error(failureMessage);
            console.error("Request failed with status code " + msg.code + ": " + msg.message);
          } else {
            if (toastOnSuccess) {
                toast.success(successMessage);
            }
          }

          return msg;
  }
}

export const authApiHelper = new AuthApiHelper();
