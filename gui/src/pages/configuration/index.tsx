import { useState, useEffect, useCallback, useRef } from "react";
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
} from "@mui/material";
import { configParamApi } from "../../apis/configuration-param-api";
// import { AuthGuard } from '../../../components/authentication/auth-guard';
import { DashboardLayout } from "../../components/dashboard-layout";
import { ConfigParamListTable } from "../../components/configuration/configuration-list-table";
import { Refresh as RefreshIcon } from "../../icons/refresh";
import { Search as SearchIcon } from "../../icons/search";

const tabs = [
  {
    label: "Query Intervals",
    value: "interval",
    description:
      "Query Interval parameters will vary the period that data will be processed by the odd\
        for the specified data feed. These are all in seconds and will change the rate of raw data gathering.",
  },
  {
    label: "SH/CADS-IFM",
    value: "cads_distance",
    description:
      "These parameters control how and when Speed Harmonization events are generated, as well as filtering\
        CADS-IFM recommendations",
  },
  {
    label: "Data Sources",
    value: "source",
    description:
      "This page displays the current state of all the switchable data feeds in the ODD. Do not \
        manually change these variables to simulated or historical as they will not automatically start simulations\
        or replays without using the simulation page to start them. Setting a data feed back to realtime will stop any\
        current historic replay or simulation and allow for realtime data to be ingested again.",
  },
  {
    label: "Ksqldb",
    value: "ksqldb",
    description:
      "Ksqldb query windows parameters varies how long data can be queried from the ODD. After\
        data reaches the age specified in the window it will be removed from queryable tables to make room for\
        newer data. This should only be changed if there is a change to the period of data input into the ODD.",
  },
];

const applyFilters = (parameters, filters) =>
  parameters.filter((parameter) => {
    if (filters.query) {
      let queryMatched = false;
      const properties = ["name", "description"];
      properties.forEach((property) => {
        if (parameter[property].toLowerCase().includes(filters.query.toLowerCase())) {
          queryMatched = true;
        }
      });

      if (!queryMatched) {
        return false;
      }
    }

    if (filters.interval && !parameter["name"].includes("interval")) {
      return false;
    }

    if (filters.ksqldb && !parameter["name"].includes("ksqldb")) {
      return false;
    }

    if (
      filters.cads_distance &&
      !parameter["name"].includes("cads_distance") &&
      !parameter["name"].includes("sh-")
    ) {
      return false;
    }

    if (filters.source && !parameter["name"].includes("source")) {
      return false;
    }

    return true;
  });

const applyPagination = (parameters, page, rowsPerPage) =>
  parameters.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

const Page = () => {
  const isMounted = useMounted();
  const queryRef = useRef<TextFieldProps>(null);
  const [parameters, setParameters] = useState(Array<ConfigurationParameter>());
  const [currentTab, setCurrentTab] = useState("interval");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [currentDescription, setCurrentDescription] = useState("");
  const [filters, setFilters] = useState({
    query: "",
    interval: true,
    ksqldb: false,
    cads_distance: false,
    source: false,
  });

  useEffect(() => {
    updateDescription();
  }, [currentTab]);

  const getParameters = async () => {
    try {
      const data = await configParamApi.getParameters();
      console.log(data);

      setParameters(data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    getParameters();
  }, []);

  const handleTabsChange = (event, value) => {
    const updatedFilters = {
      ...filters,
      interval: false,
      ksqldb: false,
      cads_distance: false,
      source: false,
    };

    updatedFilters[value] = true;
    setCurrentTab(value);
    setFilters(updatedFilters);
    setPage(0);
    setCurrentTab(value);
  };

  const handleQueryChange = (event) => {
    event.preventDefault();
    setFilters((prevState) => ({
      ...prevState,
      query: queryRef.current?.value,
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
  const filteredParameters = applyFilters(parameters, filters);
  const paginatedParameters = applyPagination(filteredParameters, page, rowsPerPage);

  return (
    <>
      <Head>
        <title>Configuration Parameters</title>
      </Head>
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          py: 8,
        }}
      >
        <Container maxWidth="xl">
          <Box sx={{ mb: 4 }}>
            <Grid container justifyContent="space-between" spacing={3}>
              <Grid item>
                <Typography variant="h4">Configuration Parameters</Typography>
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
              onClick={getParameters}
              startIcon={<RefreshIcon fontSize="small" />}
              sx={{ m: 1 }}
            >
              Refresh
            </Button>
          </Box>
          <Card>
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

            <ConfigParamListTable
              parameters={paginatedParameters}
              parametersCount={filteredParameters.length}
              onPageChange={handlePageChange}
              onRowsPerPageChange={handleRowsPerPageChange}
              rowsPerPage={rowsPerPage}
              page={page}
            />
          </Card>
        </Container>
      </Box>
    </>
  );
};

Page.getLayout = (page) => <DashboardLayout>{page}</DashboardLayout>;

export default Page;
