import { useState, useCallback, useEffect } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Box, Container, Typography } from "@mui/material";
import { configParamApi } from "../../../apis/configuration-param-api";
import { DashboardLayout } from "../../../components/dashboard-layout";
import { ConfigParamEditForm } from "../../../components/configuration/configuration-edit-form";

const ConfigParamEdit = () => {
  const [parameter, setParameter] = useState<ConfigurationParameter | null>(null);

  const router = useRouter();
  const { parameterName } = router.query;

  const getParameter = async (name: string) => {
    try {
      const data = await configParamApi.getParameter("token", name);

      setParameter(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getParameter(parameterName as string);
  }, []);

  if (!parameter) {
    return null;
  }

  return (
    <>
      <Head>
        <title>Parameter Edit</title>
      </Head>
      <Box
        component="main"
        sx={{
          backgroundColor: "background.default",
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container maxWidth="md">
          <Box
            sx={{
              alignItems: "center",
              display: "flex",
              overflow: "hidden",
            }}
          >
            <div>
              <Typography noWrap variant="h4">
                {parameter.name}
              </Typography>
            </div>
          </Box>
          <Box mt={3}>
            <ConfigParamEditForm parameter={parameter} configParamApi={configParamApi} />
          </Box>
        </Container>
      </Box>
    </>
  );
};

ConfigParamEdit.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default ConfigParamEdit;
