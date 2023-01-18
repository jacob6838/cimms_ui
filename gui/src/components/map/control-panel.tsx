import * as React from "react";
import Slider from "@mui/material/Slider";

function ControlPanel(props) {
  return (
    <div
      className="control-panel"
      style={{
        padding: "20px 120px 20px 120px",
      }}
    >
      <h3>Time Slider</h3>
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
  );
}

export default React.memo(ControlPanel);
