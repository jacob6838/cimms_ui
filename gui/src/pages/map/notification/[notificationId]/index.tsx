import Head from "next/head";
import { Box, Container, Grid } from "@mui/material";
import { DashboardLayout } from "../../../../components/dashboard-layout";
import React from "react";
import { useRouter } from "next/router";
import MapTab from "../../../../components/map/map-component";

const Map = () => {
  const router = useRouter();
  const { notificationId } = router.query;

  return (
    <>
      <Head>
        <title>Dashboard | Material Kit</title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container
          maxWidth={false}
          style={{ padding: 0, width: "100%", height: "100%", display: "flex" }}
        >
          <MapTab />
        </Container>
      </Box>
    </>
  );
};

Map.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default Map;
