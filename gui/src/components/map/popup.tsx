import React, { useState, useEffect } from "react";
import Map, { Source, Layer, Popup } from "react-map-gl";

import { Container, Col } from "reactstrap";

import { Paper, Box, Typography } from "@mui/material";
import { CustomTable } from "./custom-table";

export const CustomPopup = (props) => {
  console.log("PROPS", props);

  const getPopupContent = (feature: any) => {
    switch (feature.layer.id) {
      case "bsm":
        let bsm = feature.properties;
        return (
          <Box>
            <Typography>BSM</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["id", bsm.id],
                ["message count", bsm.msgCnt],
                ["time", bsm.secMark / 1000],
                ["speed", bsm.speed],
                ["heading", bsm.heading],
              ]}
            />
          </Box>
        );
      case "mapMessage":
        let map = feature.properties;
        let connectedObjs: any[] = [];
        console.log("Map MESSAGE", map.connectsTo);
        JSON.parse(map?.connectsTo ?? "[]")?.forEach((connectsTo) => {
          connectedObjs.push(["connectsTo", connectsTo.lane]);
          connectedObjs.push(["signalGroup", connectsTo.signalGroup]);
          connectedObjs.push(["connectionID", connectsTo.connectionID]);
        });
        return (
          <Box>
            <Typography>MAP Lane</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[["laneId", map.laneId], ...connectedObjs]}
            />
          </Box>
        );

      case "connectingLanes":
        return (
          <Box>
            <Typography>Connecting Lane</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "PROTECTED_MOVEMENT_ALLOWED"],
                ["Color", "Green"],
                ["ingress lane", feature.properties?.ingressLaneId],
                ["egress lane", feature.properties?.egressLaneId],
                ["signal Group", feature.properties?.signalGroupId],
              ]}
            />
          </Box>
        );
      case "connectingLanesYellow":
        return (
          <Box>
            <Typography>Connecting Lane</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "PROTECTED_CLEARANCE"],
                ["Color", "Yellow"],
                ["ingress lane", feature.properties?.ingressLaneId],
                ["egress lane", feature.properties?.egressLaneId],
                ["signal Group", feature.properties?.signalGroupId],
              ]}
            />
          </Box>
        );
      case "connectingLanesInactive":
        return (
          <Box>
            <Typography>Connecting Lane</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "STOP_AND_REMAIN"],
                ["Color", "Red"],
                ["ingress lane", feature.properties?.ingressLaneId],
                ["egress lane", feature.properties?.egressLaneId],
                ["signal Group", feature.properties?.signalGroupId],
              ]}
            />
          </Box>
        );
      case "connectingLanesMissing":
        return (
          <Box>
            <Typography>Connecting Lane</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "unknown"],
                ["Color", "unknown"],
                ["ingress lane", feature.properties?.ingressLaneId],
                ["egress lane", feature.properties?.egressLaneId],
                ["signal Group", feature.properties?.signalGroupId],
              ]}
            />
          </Box>
        );
      case "signalStatesGreen":
        return (
          <Box>
            <Typography>Singal State</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "PROTECTED_MOVEMENT_ALLOWED"],
                ["Color", "Green"],
                ["Signal Group", feature.properties?.signalGroup],
              ]}
            />
          </Box>
        );
      case "signalStatesYellow":
        return (
          <Box>
            <Typography>Singal State</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "PROTECTED_CLEARANCE"],
                ["Color", "Yellow"],
                ["Signal Group", feature.properties?.signalGroup],
              ]}
            />
          </Box>
        );
      case "signalStatesRed":
        return (
          <Box>
            <Typography>Singal State</Typography>
            <CustomTable
              headers={["Field", "Value"]}
              data={[
                ["State", "STOP_AND_REMAIN"],
                ["Color", "Red"],
                ["Signal Group", feature.properties?.signalGroup],
              ]}
            />
          </Box>
        );
      case "invalidLaneCollection":
        let invalidLaneCollection = feature.properties;
        return <Typography>{invalidLaneCollection.description}</Typography>;
    }
    return <Typography>No Data</Typography>;
  };

  return (
    <Popup
      longitude={props.selectedFeature.clickedLocation.lng}
      latitude={props.selectedFeature.clickedLocation.lat}
      anchor="bottom"
      onClose={props.onClose}
      onOpen={() => {
        console.log("OPENING");
      }}
      maxWidth={"500px"}
    >
      {getPopupContent(props.selectedFeature.feature)}
    </Popup>
  );
};
