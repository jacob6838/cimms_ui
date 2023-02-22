import NextLink from "next/link";
import { useRouter } from "next/router";
import PropTypes from "prop-types";
import toast from "react-hot-toast";
import * as Yup from "yup";
import { useFormik } from "formik";
import dayjs, { Dayjs } from "dayjs";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  Divider,
  Grid,
  TextField,
  InputLabel,
  MenuItem,
  Select,
  InputAdornment,
} from "@mui/material";

const EVENT_TYPES = [
  "Any",
  "ConnectionOfTravelEvent",
  "IntersectionReferenceAlignmentEvent",
  "LaneDirectionOfTravelEvent",
  "ProcessingTimePeriod",
  "SignalGroupAlignmentEvent",
  "SignalStateConflictEvent",
  "SignalStateEvent",
  "SignalStateStopEvent",
  "TimeChangeDetailsEvent",
  "MapMinimumDataEvent",
  "SpatMinimumDataEvent",
  "MapBroadcastRateEvent",
  "SpatBroadcastRateEvent",
];

const ASSESSMENT_TYPES = [
  "Any",
  "SignalStateAssessment",
  "LaneDirectionOfTravelAssessment",
  "ConnectionOfTravelAssessment",
  "VehicleStopAssessment",
];

const NOTIFICATION_TYPES = [
  "Any",
  "IntersectionReferenceAlignmentNotification",
  "SignalGroupAlignmentNotification",
  "SpatMinimumDataNotification",
  "MapMinimumDataNotification",
  "SpatBroadcastRateNotification",
  "MapBroadcastRateNotification",
  "SignalStateAssessmentNotification",
  "LaneDirectionOfTravelAssessmentNotification",
  "ConnectionOfTravelNotification",
  "SignalStateConflictNotification",
  "TimeChangeDetailsNotification",
];

export const DataSelectorEditForm = (props) => {
  const { onQuery, ...other } = props;
  const router = useRouter();
  const formik = useFormik({
    initialValues: {
      type: "map",
      startDate: new Date(),
      timeRange: 0,
      intersectionId: "",
      roadRegulatorId: "",
      submit: null,

      // type specific filters
      bsmVehicleId: null,
      eventType: "Any",
      assessmentId: "Any",
      notificationId: "Any",
    },
    validationSchema: Yup.object({
      type: Yup.string().required("Type is required"),
      startDate: Yup.date().required("Start date is required"),
      timeRange: Yup.number().required("Time interval is required"),
      intersectionId: Yup.string().required("Intersection ID is required"),
      roadRegulatorId: Yup.string().required("Road Regulator ID is required"),
      bsmVehicleId: Yup.string(),
    }),
    onSubmit: async (values, helpers) => {
      try {
        helpers.setStatus({ success: true });
        helpers.setSubmitting(false);
        onQuery({
          type: values.type,
          intersectionId: values.intersectionId,
          roadRegulatorId: values.roadRegulatorId,
          startDate: values.startDate,
          timeRange: values.timeRange,
          bsmVehicleId: values.bsmVehicleId,
        });
      } catch (err) {
        console.error(err);
        toast.error("Something went wrong!");
        helpers.setStatus({ success: false });
        helpers.setErrors({ submit: err.message });
        helpers.setSubmitting(false);
      }
    },
  });

  const onTypeChange = (newType) => {
    // formik.setFieldValue("bsmVehicleId", null);
    // formik.setFieldValue("eventType", "Any");
    // formik.setFieldValue("assessmentId", "Any");
    // formik.setFieldValue("notificationId", "Any");
  };

  const getTypeSpecificFilters = (type) => {
    switch (type) {
      case "bsm":
        return (
          <>
            <Grid item md={6} xs={12}>
              <TextField
                error={Boolean(formik.touched.bsmVehicleId && formik.errors.bsmVehicleId)}
                fullWidth
                helperText={formik.touched.bsmVehicleId && formik.errors.bsmVehicleId}
                label="Vehicle ID"
                name="bsmVehicleId"
                onChange={formik.handleChange}
                value={formik.values.bsmVehicleId}
              />
            </Grid>
          </>
        );
      case "events":
        return (
          <>
            <Grid item md={6} xs={12}>
              <InputLabel variant="standard" htmlFor="uncontrolled-native">
                Event Type
              </InputLabel>
              <Select
                error={Boolean(formik.touched.eventType && formik.errors.eventType)}
                // fullWidth
                value={formik.values.eventType}
                // label="Query Type"
                label="Event Type"
                helperText={formik.touched.eventType && formik.errors.eventType}
                onChange={(e) => {
                  onTypeChange(e.target.value);
                  formik.setFieldValue("eventType", e.target.value);
                }}
                onBlur={formik.handleBlur}
              >
                {EVENT_TYPES.map((eventType) => (
                  <MenuItem value={eventType}>{eventType}</MenuItem>
                ))}
              </Select>
            </Grid>
          </>
        );
      case "assessments":
        return (
          <>
            <Grid item md={6} xs={12}>
              <InputLabel variant="standard" htmlFor="uncontrolled-native">
                Assessment Type
              </InputLabel>
              <Select
                error={Boolean(formik.touched.assessmentId && formik.errors.assessmentId)}
                // fullWidth
                value={formik.values.assessmentId}
                // label="Query Type"
                label="Assessment Type"
                helperText={formik.touched.assessmentId && formik.errors.assessmentId}
                onChange={(e) => {
                  onTypeChange(e.target.value);
                  formik.setFieldValue("assessmentId", e.target.value);
                }}
                onBlur={formik.handleBlur}
              >
                {ASSESSMENT_TYPES.map((assessmentType) => (
                  <MenuItem value={assessmentType}>{assessmentType}</MenuItem>
                ))}
              </Select>
            </Grid>
          </>
        );
      case "notifications":
        return (
          <>
            <Grid item md={6} xs={12}>
              <InputLabel variant="standard" htmlFor="uncontrolled-native">
                Notification Type
              </InputLabel>
              <Select
                error={Boolean(formik.touched.notificationId && formik.errors.notificationId)}
                // fullWidth
                value={formik.values.notificationId}
                label="Notification Type"
                helperText={formik.touched.notificationId && formik.errors.notificationId}
                onChange={(e) => {
                  onTypeChange(e.target.value);
                  formik.setFieldValue("notificationId", e.target.value);
                }}
                onBlur={formik.handleBlur}
              >
                {NOTIFICATION_TYPES.map((notificationType) => (
                  <MenuItem value={notificationType}>{notificationType}</MenuItem>
                ))}
              </Select>
            </Grid>
          </>
        );
      default:
        return <></>;
    }
  };

  return (
    <form onSubmit={formik.handleSubmit} {...other}>
      <Card>
        {/* <CardHeader title="Edit Configuration Parameter" /> */}
        <Divider />
        <CardContent>
          <Grid container spacing={3}>
            <Grid item md={6} xs={12}>
              <TextField
                error={Boolean(formik.touched.intersectionId && formik.errors.intersectionId)}
                fullWidth
                helperText={formik.touched.intersectionId && formik.errors.intersectionId}
                label="Intersection ID"
                name="intersectionId"
                onChange={formik.handleChange}
                value={formik.values.intersectionId}
              />
            </Grid>
            <Grid item md={6} xs={12}>
              <TextField
                error={Boolean(formik.touched.roadRegulatorId && formik.errors.roadRegulatorId)}
                fullWidth
                helperText={formik.touched.roadRegulatorId && formik.errors.roadRegulatorId}
                label="Road Regulator ID"
                name="roadRegulatorId"
                onChange={formik.handleChange}
                value={formik.values.roadRegulatorId}
              />
            </Grid>
            <Grid item md={4} xs={12}>
              {/* <InputLabel variant="standard" htmlFor="uncontrolled-native">
                Query Type
              </InputLabel> */}
              <Select
                error={Boolean(formik.touched.type && formik.errors.type)}
                // fullWidth
                value={formik.values.type}
                // label="Query Type"
                label="Type"
                helperText={formik.touched.type && formik.errors.type}
                onChange={(e) => {
                  onTypeChange(e.target.value);
                  formik.setFieldValue("type", e.target.value);
                }}
                onBlur={formik.handleBlur}
              >
                <MenuItem value={"map"}>MAP</MenuItem>
                <MenuItem value={"spat"}>SPAT</MenuItem>
                <MenuItem value={"bsm"}>BSM</MenuItem>
                <MenuItem value={"events"}>Events</MenuItem>
                <MenuItem value={"assessments"}>Assessments</MenuItem>
                <MenuItem value={"notifications"}>Notifications</MenuItem>
              </Select>
            </Grid>
            <Grid item md={4} xs={12}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DateTimePicker
                  renderInput={(props) => (
                    <TextField
                      {...props}
                      error={Boolean(formik.touched.startDate && formik.errors.startDate)}
                      helperText={formik.touched.startDate && formik.errors.startDate}
                      name="startDate"
                      label="Start Date"
                      //   fullWidth
                    />
                  )}
                  value={formik.values.startDate}
                  onChange={(e) => formik.setFieldValue("startDate", e, true)}
                />
              </LocalizationProvider>
            </Grid>
            <Grid item md={4} xs={12}>
              <TextField
                // fullWidth
                helperText={formik.touched.timeRange && formik.errors.timeRange}
                label="Time Range"
                name="timeRange"
                type="number"
                onBlur={formik.handleBlur}
                onChange={formik.handleChange}
                InputProps={{
                  endAdornment: <InputAdornment position="end">minutes</InputAdornment>,
                }}
                value={formik.values.timeRange}
              />
            </Grid>
            {getTypeSpecificFilters(formik.values.type)}
          </Grid>
        </CardContent>
        <CardActions
          sx={{
            flexWrap: "wrap",
            m: -1,
          }}
        >
          <Button disabled={formik.isSubmitting} type="submit" sx={{ m: 1 }} variant="contained">
            Query Data
          </Button>
          <NextLink href="/notifications" passHref>
            <Button
              component="a"
              disabled={formik.isSubmitting}
              sx={{
                m: 1,
                mr: "auto",
              }}
              variant="outlined"
            >
              Cancel
            </Button>
          </NextLink>
        </CardActions>
      </Card>
    </form>
  );
};

DataSelectorEditForm.propTypes = {
  onQuery: PropTypes.func.isRequired,
};
