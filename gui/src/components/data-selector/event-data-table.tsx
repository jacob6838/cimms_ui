import Head from "next/head";
import {
  Box,
  Button,
  Card,
  Container,
  Divider,
  Grid,
  InputAdornment,
  Stack,
  Tab,
  Tabs,
  TextField,
  TextFieldProps,
  Typography,
  CardHeader,
} from "@mui/material";
import { EventListResults } from "./event-list-results";
import { DashboardLayout } from "../dashboard-layout";
import { Refresh as RefreshIcon } from "../../icons/refresh";
import { Search as SearchIcon } from "../../icons/search";
import NotificationApi from "../../apis/notification-api";
import React, { useEffect, useState, useRef } from "react";

const applyPagination = (parameters, page, rowsPerPage) =>
  parameters.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

export const EventDataTable = (props: {
  events: MessageMonitor.Event[];
  onDownload: () => void;
}) => {
  const { events, onDownload } = props;
  const queryRef = useRef<TextFieldProps>(null);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [currentDescription, setCurrentDescription] = useState("");

  const handlePageChange = (event, newPage) => {
    setPage(newPage);
  };

  const handleRowsPerPageChange = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  // Usually query is done on backend with indexing solutions
  const paginatedNotifications = applyPagination(events, page, rowsPerPage);

  return (
    <>
      <Container maxWidth={false}>
        <Card>
          <>
            <CardHeader title="Data" />
            <Divider />
          </>

          <EventListResults
            events={paginatedNotifications}
            eventsCount={events.length}
            onPageChange={handlePageChange}
            onRowsPerPageChange={handleRowsPerPageChange}
            rowsPerPage={rowsPerPage}
            page={page}
          />
        </Card>
        <Box sx={{ mb: 4 }}>
          <Box
            sx={{
              m: -1,
              mt: 3,
            }}
          >
            <Grid container justifyContent="left" spacing={3}>
              <Grid item>
                <Button
                  sx={{ m: 1 }}
                  variant="contained"
                  onClick={onDownload}
                  disabled={events.length <= 0 ? true : false}
                >
                  Download
                </Button>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </>
  );
};
