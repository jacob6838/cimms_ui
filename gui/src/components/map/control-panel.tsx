import * as React from "react";
import Slider from "@mui/material/Slider";

function ControlPanel(props) {
  return (
    <div className="control-panel">
      <h3>Animated GeoJSON</h3>
      <p>Render animation by updating GeoJSON data source.</p>
      <div className="source-link">
        <a
          href="https://github.com/visgl/react-map-gl/tree/7.0-release/examples/geojson-animation"
          target="_new"
        >
          View Code ↗
        </a>
      </div>
      <Slider
        aria-label="Volume"
        value={props.sliderValue}
        onChange={props.setSlider}
        marks={props.marks}
        // min={0}
        // max={props.marks.length - 1}
        valueLabelDisplay="auto"
      />
    </div>
  );
}

export default React.memo(ControlPanel);
