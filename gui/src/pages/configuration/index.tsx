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
import toast from "react-hot-toast";

const tabs = [
  {
    label: "General",
    value: "GENERAL",
    description: "General configuration parameters",
  },
  {
    label: "Default",
    value: "DEFAULT",
    description: "Broad non-intersection specific configuration parameters",
  },
  {
    label: "Intersection",
    value: "INTERSECTION",
    description: "Intersection specific configurable configuration parameters",
  },
  {
    label: "Debug",
    value: "DEBUG",
    description: "Signal state configuration parameters",
  },
  {
    label: "Read Only",
    value: "READ_ONLY",
    description: "Signal state configuration parameters",
  },
  {
    label: "All",
    value: "all",
    description: "All Configuration Parameters",
  },
];

const applyFilters = (parameters, filter) =>
  parameters.filter((parameter) => {
    if (filter.query) {
      let queryMatched = false;
      const properties = ["name", "category", "description"];
      properties.forEach((property) => {
        if (parameter[property]?.toLowerCase().includes(filter.query.toLowerCase())) {
          queryMatched = true;
        }
      });

      if (!queryMatched) {
        return false;
      }
    }

    if (filter.tab === "all") {
      return true;
    } else if (filter.tab === "GENERAL") {
      return parameter["updateType"] !== "READ_ONLY" && !parameter["key"].includes("debug");
    } else if (filter.tab === "DEBUG") {
      return parameter["key"].includes("debug");
    } else {
      return parameter["updateType"] == filter.tab && !parameter["key"].includes("debug");
    }
  });

const applyPagination = (parameters, page, rowsPerPage) =>
  parameters.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage);

const Page = () => {
  const queryRef = useRef<TextFieldProps>(null);
  const [parameters, setParameters] = useState<Config[]>([]);
  const [currentTab, setCurrentTab] = useState("GENERAL");
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [currentDescription, setCurrentDescription] = useState("");
  const [filter, setFilter] = useState({
    query: "",
    tab: currentTab,
  });

  useEffect(() => {
    updateDescription();
  }, [currentTab]);

  const getParameters = async () => {
    try {
      const data = await configParamApi.getAllParameters("token", "12109");
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
  const filteredParameters = applyFilters(parameters, filter);
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
                  Configuration Parameters
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
