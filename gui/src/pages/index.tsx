import Head from "next/head";
import { Box, Container, Grid } from "@mui/material";
import { Budget } from "../components/dashboard/budget";
import { LatestOrders } from "../components/dashboard/latest-orders";
import { LatestProducts } from "../components/dashboard/latest-products";
import { Sales } from "../components/dashboard/sales";
import { TasksProgress } from "../components/dashboard/tasks-progress";
import { TotalCustomers } from "../components/dashboard/total-customers";
import { TotalProfit } from "../components/dashboard/total-profit";
import { TrafficByDevice } from "../components/dashboard/traffic-by-device";
import { DashboardLayout } from "../components/dashboard-layout";
import { NotificationsTable } from "../components/notifications/notifications-table";
import { ConnectionOfTravelAssessmentCard } from "../components/assessments/connection-of-travel-assessment";
import { LaneDirectionOfTravelAssessmentCard } from "../components/assessments/lane-direction-of-travel-assessment";
import { SignalStateAssessmentCard } from "../components/assessments/signal-state-assessment";
import React, { useEffect, useState, useRef } from "react";
import MessageMonitorApi from "../apis/mm-api";

const Page = () => {
  const [assessment, setAssessments] = useState<Assessment[]>([]);

  // create hooks, and methods for each assessment type:
  const [signalStateAssessment, setSignalStateAssessment] = useState<SignalStateAssessment | null>(
    null
  );
  const [connectionOfTravelAssessment, setConnectionOfTravelAssessment] =
    useState<ConnectionOfTravelAssessment | null>(null);
  const [laneDirectionOfTravelAssessment, setLaneDirectionOfTravelAssessment] =
    useState<LaneDirectionOfTravelAssessment | null>(null);

  const getAssessments = () => {
    setSignalStateAssessment(MessageMonitorApi.getSignalStateAssessment());
    setConnectionOfTravelAssessment(MessageMonitorApi.getConnectionOfTravelAssessment());
    setLaneDirectionOfTravelAssessment(MessageMonitorApi.getLaneDirectionOfTravelAssessment());
  };

  useEffect(() => {
    getAssessments();
  }, []);

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
        <Container maxWidth={false}>
          <Grid container spacing={3}>
            <Grid item lg={3} sm={6} xl={3} xs={12}>
              <ConnectionOfTravelAssessmentCard assessment={connectionOfTravelAssessment} />
            </Grid>
            <Grid item xl={3} lg={3} sm={6} xs={12}>
              <LaneDirectionOfTravelAssessmentCard assessment={laneDirectionOfTravelAssessment} />
            </Grid>
            <Grid item xl={3} lg={3} sm={6} xs={12}>
              <SignalStateAssessmentCard assessment={signalStateAssessment} />
            </Grid>
            <Grid item lg={12} md={12} xl={12} xs={12}>
              <NotificationsTable simple={true} />
            </Grid>
            <Grid item lg={8} md={12} xl={9} xs={12}>
              <Sales />
            </Grid>
            <Grid item lg={4} md={6} xl={3} xs={12}>
              <TrafficByDevice sx={{ height: "100%" }} />
            </Grid>
            <Grid item lg={4} md={6} xl={3} xs={12}>
              <LatestProducts sx={{ height: "100%" }} />
            </Grid>
            <Grid item lg={8} md={12} xl={9} xs={12}>
              <LatestOrders />
            </Grid>
          </Grid>
        </Container>
      </Box>
    </>
  );
};

Page.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default Page;
