import toast from 'react-hot-toast';

const MESSAGE_MONITOR_ENDPOINT = process.env.MESSAGE_MONITOR_API_ENDPOINT;

class AuthApigHelper {

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
        console.log("MAKING REQUEST TO", MESSAGE_MONITOR_ENDPOINT! + "/" + path + new URLSearchParams(queryParams))
          const results = await fetch(MESSAGE_MONITOR_ENDPOINT! + "/" + path + new URLSearchParams(queryParams), {
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

export const authApigHelper = new AuthApigHelper();
