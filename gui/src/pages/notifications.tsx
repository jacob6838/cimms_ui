import Head from "next/head";
import { Box, Container } from "@mui/material";
import { CustomerListResults } from "../components/notifications/customer-list-results"; //customer-list-results
import { CustomerListToolbar } from "../components/notifications/customer-list-toolbar";
import { DashboardLayout } from "../components/dashboard-layout";
import { Button } from "@mui/material";
import MessageMonitorApi from "../apis/mm-api";
import React, { useEffect, useState } from "react";

const Page = () => {
  const [notifications, setNotifications] = useState<SpatBroadcastRateNotification>([]);

  useEffect(() => {
    setNotifications(MessageMonitorApi.getNotifications());
  }, []);

  return (
    <>
      <Head>
        <title>Notifications | Material Kit</title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container maxWidth={false}>
          <CustomerListToolbar />
          <Box sx={{ mt: 3 }}>
            <CustomerListResults customers={notifications} />
          </Box>

          <Box sx={{ m: 1 }}>
            <Button color="primary" variant="contained">
              Mark Accepted
            </Button>
          </Box>
        </Container>
      </Box>
    </>
  );
};

Page.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default Page;
