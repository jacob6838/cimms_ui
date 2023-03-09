import { useState, useCallback, useEffect } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Box, Container, Typography } from "@mui/material";
import { configParamApi } from "../apis/configuration-param-api";
import { DashboardLayout } from "../components/dashboard-layout";
import { DataSelectorEditForm } from "../components/data-selector/data-selector-edit-form";

const DataSelectorPage = () => {
  const router = useRouter();
  const { parameterName } = router.query;

  //   const getParameter = async (name: string) => {
  //     try {
  //       const data = await configParamApi.getParameter("token", name);

  //       setParameter(data);
  //     } catch (err) {
  //       console.error(err);
  //     }
  //   };

  //   useEffect(() => {
  //     getParameter(parameterName as string);
  //   }, []);

  //   if (!parameter) {
  //     return null;
  //   }

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
                Query
              </Typography>
            </div>
          </Box>
          <Box mt={3}>
            <DataSelectorEditForm onQuery={() => {}} />
          </Box>
        </Container>
      </Box>
    </>
  );
};

DataSelectorPage.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default DataSelectorPage;
