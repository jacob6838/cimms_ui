import * as React from "react";
import Slider from "@mui/material/Slider";
import dayjs, { Dayjs } from "dayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import {
  AppBar,
  Avatar,
  Badge,
  Box,
  IconButton,
  Toolbar,
  Tooltip,
  Theme,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  TextField,
} from "@mui/material";

const EVENT_TYPES = [
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

function ControlPanel(props) {
  return (
    <div
      style={{
        padding: "20px 20px 20px 20px",
      }}
    >
      <Box>
        <FormControl sx={{ width: 400, ml: 1, mt: 1 }} variant="filled">
          <InputLabel id="demo-simple-select-label">Event Type</InputLabel>
          <Select
            labelId="demo-simple-select-label"
            id="demo-simple-select"
            value={props.eventType}
            label="Age"
            onChange={(e) => {}}
          >
            {EVENT_TYPES.map((val) => {
              return <MenuItem value={val}>{val}</MenuItem>;
            })}
          </Select>
        </FormControl>
      </Box>

      <Box sx={{ mt: 1 }}>
        <h3>Time Interval</h3>
        <Box sx={{ mt: 1 }}>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateTimePicker
              label="Start date"
              value={dayjs(props.startDate ?? new Date())}
              onChange={(e) => {}}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateTimePicker
              label="End date"
              value={dayjs(props.endDate ?? new Date())}
              onChange={(e) => {}}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
        </Box>
      </Box>
      <div
        className="control-panel"
        style={{
          padding: "20px 100px 0px 100px",
        }}
      >
        <h3>Visualization Time</h3>
        <Slider
          aria-label="Volume"
          value={props.sliderValue}
          onChange={props.setSlider}
          marks={props.marks}
          min={0}
          max={props.marks.at(-1)?.value}
          valueLabelDisplay="auto"
        />
      </div>
    </div>
  );
}

export default React.memo(ControlPanel);
