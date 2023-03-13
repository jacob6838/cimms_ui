import { useState, useCallback, useEffect } from "react";
import Head from "next/head";
import { useRouter } from "next/router";
import { Box, Container, Typography } from "@mui/material";
import EventsApi from "../apis/events-api";
import AssessmentsApi from "../apis/assessments-api";
import { DashboardLayout } from "../components/dashboard-layout";
import { DataSelectorEditForm } from "../components/data-selector/data-selector-edit-form";
import { EventDataTable } from "../components/data-selector/event-data-table";
import { AssessmentDataTable } from "../components/data-selector/assessment-data-table";
import { useDashboardContext } from "../contexts/dashboard-context";

const DataSelectorPage = () => {
  const [type, setType] = useState("");
  const [events, setEvents] = useState<MessageMonitor.Event[]>([]);
  const [assessments, setAssessments] = useState<Assessment[]>([]);
  const { intersectionId: dbIntersectionId } = useDashboardContext();

  const downloadTxtFile = (contents: string, type: string) => {
    const element = document.createElement("a");
    const file = new Blob([contents], { type: "text/plain" });
    element.href = URL.createObjectURL(file);
    element.download = `${type}.txt`;
    document.body.appendChild(element); // Required for this to work in FireFox
    element.click();
  };

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
    setType(type);
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
        setAssessments([]);
        return events;
      case "assessments":
        console.log("Assessments");
        const assessments: Assessment[] = [];
        // iterate through each event type in a for loop and add the events to events array
        for (let i = 0; i < assessmentTypes.length; i++) {
          const eventType = assessmentTypes[i];
          const event = await AssessmentsApi.getAssessment(
            "token",
            eventType,
            intersectionId,
            startDate,
            endTime
          );
          assessments.push(...event);
          console.log(eventType, event);
        }
        console.log(assessments);
        setAssessments(assessments);
        setEvents([]);
        return assessments;
    }
    return;
  };

  return (
    <>
      <Head>
        <title>Data Selector</title>
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
              dbIntersectionId={dbIntersectionId}
            />
          </Box>
        </Container>

        {type == "events" && (
          <EventDataTable
            events={events}
            onDownload={() => {
              return downloadTxtFile(events.map((e) => JSON.stringify(e)).join("\n"), "events");
            }}
          />
        )}
        {type == "assessments" && (
          <AssessmentDataTable
            events={assessments}
            onDownload={() => {
              return downloadTxtFile(
                assessments.map((e) => JSON.stringify(e)).join("\n"),
                "assessments"
              );
            }}
          />
        )}
      </Box>
    </>
  );
};

DataSelectorPage.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default DataSelectorPage;
