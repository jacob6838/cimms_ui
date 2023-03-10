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
import { CustomerListResults } from "./customer-list-results";
import { DashboardLayout } from "../dashboard-layout";
import { Refresh as RefreshIcon } from "../../icons/refresh";
import { Search as SearchIcon } from "../../icons/search";
import MessageMonitorApi from "../../apis/mm-api";
import React, { useEffect, useState, useRef } from "react";

const tabs = [
  {
    label: "All",
    value: "all",
    description: "All Notifications",
  },
  {
    label: "SBR",
    value: "SpatBroadcastRateNotification",
    description: "Spat Broadcast Rate Notification",
  },
  {
    label: "IRA",
    value: "IntersectionReferenceAlignmentNotification",
    description: "Intersection Reference Alignment Notification",
  },
  {
    label: "SGA",
    value: "SignalGroupAlignmentNotification",
    description: "Signal Group Alignment Notification",
  },
  {
    label: "SMA",
    value: "SpatMinimumDataNotification",
    description: "Spat Minimum Data Notification",
  },
  {
    label: "MMD",
    value: "MapMinimumDataNotification",
    description: "Map Minimum Data Notification",
  },
  {
    label: "MBR",
    value: "MapBroadcastRateNotification",
    description: "Map Broadcast Rate Notification",
  },
  {
    label: "SSA",
    value: "SignalStateAssessmentNotification",
    description: "Signal State Assessment Notification",
  },
  {
    label: "LDOTA",
    value: "LaneDirectionOfTravelAssessmentNotification",
    description: "Lane Direction of Travel Assessment Notification",
  },
  {
    label: "COT",
    value: "ConnectionOfTravelNotification",
    description: "Connection of Travel Notification",
  },
  {
    label: "SSC",
    value: "SignalStateConflictNotification",
    description: "Signal State Conflict Notification",
  },
  {
    label: "TCD",
    value: "TimeChangeDetailsNotification",
    description: "Time Change Details Notification",
  },
];

const applyFilters = (parameters, filter) =>
  parameters.filter((parameter) => {
    if (filter.query) {
      let queryMatched = false;
      const properties = ["notificationType", "notificationText"];
      properties.forEach((property) => {
        if (parameter[property].toLowerCase().includes(filter.query.toLowerCase())) {
          queryMatched = true;
        }
      });

      if (!queryMatched) {
        return false;
      }
    }

    if (filter.tab === "all") {
      return true;
    }

    return parameter["notificationType"] == filter.tab;
  });

const applyPagination = (parameters, page, rowsPerPage) =>
  parameters.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

export const NotificationsTable = () => {
  const queryRef = useRef<TextFieldProps>(null);
  const [notifications, setNotifications] = useState<SpatBroadcastRateNotification>([]);
  const [acceptedNotifications, setAcceptedNotifications] = useState<String[]>([]);
  const [currentTab, setCurrentTab] = useState("all");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [currentDescription, setCurrentDescription] = useState("");
  const [filter, setFilter] = useState({
    query: "",
    tab: currentTab,
  });

  const updateNotifications = () => {
    setNotifications(MessageMonitorApi.getNotifications());
  };

  useEffect(() => {
    updateNotifications();
  }, []);

  useEffect(() => {
    updateDescription();
  }, [currentTab]);

  const handleTabsChange = (event, value) => {
    const updatedFilter = { ...filter, tab: value };
    setCurrentTab(value);
    setFilter(updatedFilter);
    setPage(0);
    setCurrentTab(value);
  };

  const handleQueryChange = (event) => {
    event.preventDefault();
    setFilter((prevState) => ({
      ...prevState,
      query: queryRef.current?.value as string,
    }));
  };

  const handlePageChange = (event, newPage) => {
    setPage(newPage);
  };

  const handleRowsPerPageChange = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const updateDescription = () => {
    for (let i = 0; i < tabs.length; i++) {
      if (tabs[i].value === currentTab) {
        setCurrentDescription(tabs[i].description);
      }
    }
  };

  // Usually query is done on backend with indexing solutions
  const filteredNotifications = applyFilters(notifications, filter);
  const paginatedNotifications = applyPagination(filteredNotifications, page, rowsPerPage);

  return (
    <>
      <Container maxWidth={false}>
        <Box
          sx={{
            alignItems: "center",
            display: "flex",
            justifyContent: "space-between",
            flexWrap: "wrap",
            m: -1,
          }}
        >
          <Grid container justifyContent="space-between" spacing={3}>
            <Grid item>
              <Typography sx={{ m: 1 }} variant="h4">
                Notifications
              </Typography>
            </Grid>
          </Grid>
          <Box
            sx={{
              m: -1,
              mt: 3,
            }}
          ></Box>
        </Box>
        <Box
          sx={{
            m: -1,
            mt: 3,
            mb: 3,
          }}
        >
          <Button
            color="primary"
            variant="contained"
            onClick={updateNotifications}
            startIcon={<RefreshIcon fontSize="small" />}
            sx={{ m: 1 }}
          >
            Refresh
          </Button>
        </Box>
        <Card>
          <CardHeader title="Notifications" />
          <Tabs
            indicatorColor="primary"
            onChange={handleTabsChange}
            scrollButtons="auto"
            sx={{ px: 3 }}
            textColor="primary"
            value={currentTab}
            variant="scrollable"
          >
            {tabs.map((tab) => (
              <Tab key={tab.value} label={tab.label} value={tab.value} />
            ))}
          </Tabs>
          <Divider />
          <Box
            sx={{
              alignItems: "center",
              display: "flex",
              flexWrap: "wrap",
              m: -1.5,
              p: 3,
            }}
          >
            <Stack>
              <Box
                component="form"
                onSubmit={handleQueryChange}
                sx={{
                  flexGrow: 1,
                  m: 1.5,
                }}
              >
                <TextField
                  defaultValue=""
                  fullWidth
                  inputProps={{ ref: queryRef }}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">
                        <SearchIcon fontSize="small" />
                      </InputAdornment>
                    ),
                  }}
                  placeholder="Search parameters"
                />
              </Box>
              <Typography variant="body1">{currentDescription}</Typography>
            </Stack>
          </Box>

          <CustomerListResults
            customers={paginatedNotifications}
            allTabNotifications={notifications}
            notificationsCount={filteredNotifications.length}
            selectedNotifications={acceptedNotifications}
            onSelectedItemsChanged={setAcceptedNotifications}
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
                  onClick={() => {}}
                  disabled={acceptedNotifications.length <= 0 ? true : false}
                >
                  Dismiss Notifications
                </Button>
              </Grid>
            </Grid>
          </Box>
        </Box>
      </Container>
    </>
  );
};
