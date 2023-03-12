import { useState, useCallback, useEffect } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Box, Container, Typography } from "@mui/material";
import EventsApi from "../apis/events-api";
import { DashboardLayout } from "../components/dashboard-layout";
import { DataSelectorEditForm } from "../components/data-selector/data-selector-edit-form";
import { EventDataTable } from "../components/data-selector/event-data-table";

const DataSelectorPage = () => {
  const [events, setEvents] = useState<MessageMonitor.Event[]>([]);
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

  useEffect(() => {
    console.log("EVENT CHANGED", events);
  }, events);

  const query = async ({
    type,
    intersectionId,
    roadRegulatorId,
    startDate,
    timeRange,
    eventTypes,
    assessmentTypes,
    bsmVehicleId,
  }) => {
    console.log("QUERYING DATA");
    const endTime = new Date(startDate.getTime() + timeRange * 60 * 1000);
    switch (type) {
      case "events":
        console.log("EVENTS");
        const events: MessageMonitor.Event[] = [];
        // iterate through each event type in a for loop and add the events to events array
        for (let i = 0; i < eventTypes.length; i++) {
          const eventType = eventTypes[i];
          const event = await EventsApi.getEvent(
            "token",
            eventType,
            intersectionId,
            startDate,
            endTime
          );
          events.push(...event);
          console.log(eventType, event);
        }
        console.log(events);
        setEvents(events);
        return events;
    }
    return;
  };

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
            <DataSelectorEditForm
              onQuery={({
                type,
                intersectionId,
                roadRegulatorId,
                startDate,
                timeRange,
                eventTypes,
                assessmentTypes,
                bsmVehicleId,
              }) =>
                query({
                  type,
                  intersectionId,
                  roadRegulatorId,
                  startDate,
                  timeRange,
                  eventTypes,
                  assessmentTypes,
                  bsmVehicleId,
                })
              }
            />
          </Box>
        </Container>

        <EventDataTable events={events} />
      </Box>
    </>
  );
};

DataSelectorPage.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default DataSelectorPage;
