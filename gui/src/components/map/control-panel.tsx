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
  Button,
} from "@mui/material";

function ControlPanel(props) {
  return (
    <div
      style={{
        padding: "20px 20px 20px 20px",
      }}
    >
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
