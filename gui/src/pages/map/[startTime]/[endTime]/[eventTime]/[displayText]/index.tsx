import Head from "next/head";
import { Box, Container } from "@mui/material";
import { DashboardLayout } from "../../../../../../components/dashboard-layout";
import React from "react";
import MapTab from "../../../../../../components/map/map-component";
import { useRouter } from "next/router";

const Map = () => {
  const router = useRouter();
  const { startTime, endTime, displayText, eventTime } = router.query;

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
          <MapTab
            startDate={new Date(Number(startTime))}
            endDate={new Date(Number(endTime))}
            eventDate={new Date(Number(eventTime))}
            displayText={displayText as string}
          />
        </Container>
      </Box>
    </>
  );
};

Map.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default Map;
