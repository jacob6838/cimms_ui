import React, { useState, useEffect } from "react";
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
  Button,
  InputAdornment,
} from "@mui/material";
import { NoMeals } from "@mui/icons-material";

function ControlPanel(props) {
  const getQueryParams = ({
    startDate,
    endDate,
    eventDate,
    timeWindowSeconds,
  }: {
    startDate: Date;
    endDate: Date;
    eventDate: Date;
    timeWindowSeconds: number;
  }) => {
    return {
      eventTime: eventDate,
      timeBefore: Math.round((eventDate.getTime() - startDate.getTime()) / 1000),
      timeAfter: Math.round((endDate.getTime() - eventDate.getTime()) / 1000),
      timeWindowSeconds: timeWindowSeconds,
    };
  };

  const [dateParams, setDateParams] = useState<{
    eventTime?: Date;
    timeBefore?: number;
    timeAfter?: number;
    timeWindowSeconds?: number;
  }>(getQueryParams(props.timeQueryParams));

  useEffect(() => {
    props.onTimeQueryChanged(dateParams.eventTime, dateParams.timeBefore, dateParams.timeAfter);
  }, [dateParams]);

  const getNumber = (value: string): number | undefined => {
    const num = parseInt(value);
    if (isNaN(num)) {
      return undefined;
    }
    return num;
  };

  return (
    <div
      style={{
        padding: "20px 20px 20px 20px",
      }}
    >
      <Box sx={{ mt: 1 }}>
        <h3>Time Interval</h3>
        <Box sx={{ mt: 1 }}>
          <TextField
            label="Time Before Event"
            name="timeRangeBefore"
            type="number"
            onChange={(e) => {
              setDateParams((prevState) => {
                return { ...prevState, timeBefore: getNumber(e.target.value) };
              });
            }}
            InputProps={{
              endAdornment: <InputAdornment position="end">seconds</InputAdornment>,
            }}
            value={dateParams.timeBefore}
          />
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DateTimePicker
              label="Event Date"
              value={dayjs(dateParams.eventTime ?? new Date())}
              onChange={(e) => {
                setDateParams((prevState) => {
                  return { ...prevState, eventTime: e?.toDate() };
                });
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
          <TextField
            // fullWidth
            label="Time After Event"
            name="timeRangeAfter"
            type="number"
            onChange={(e) => {
              setDateParams((prevState) => {
                return { ...prevState, timeAfter: getNumber(e.target.value) };
              });
            }}
            InputProps={{
              endAdornment: <InputAdornment position="end">seconds</InputAdornment>,
            }}
            value={dateParams.timeAfter}
          />
          <TextField
            // fullWidth
            label="Time Render Window"
            name="timeRangeAfter"
            type="number"
            onChange={(e) => {
              setDateParams((prevState) => {
                return { ...prevState, timeWindowSeconds: getNumber(e.target.value) };
              });
            }}
            InputProps={{
              endAdornment: <InputAdornment position="end">seconds</InputAdornment>,
            }}
            value={dateParams.timeWindowSeconds}
          />
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
          //   value={[20, 37]}
          //   onChange={() => {}}
          value={props.sliderValue}
          onChange={props.setSlider}
          //   marks={props.marks}
          min={0}
          max={props.max}
          valueLabelDisplay="auto"
          disableSwap
        />
        <Button sx={{ m: 1 }} variant="contained" onClick={props.downloadAllData}>
          Download
        </Button>
      </div>
    </div>
  );
}

export default React.memo(ControlPanel);
