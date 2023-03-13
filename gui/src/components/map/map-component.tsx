import React, { useState, useEffect } from "react";
import Map, { Source, Layer } from "react-map-gl";

import { Container, Col } from "reactstrap";

import { Paper, Box } from "@mui/material";

// import mapMessageData from "./processed_map_v4.json";
import type { LayerProps } from "react-map-gl";
import ControlPanel from "./control-panel";
import MessageMonitorApi from "../../apis/mm-api";
import { useDashboardContext } from "../../contexts/dashboard-context";

const emptyFeatureCollection = {
  type: "FeatureCollection",
  features: [],
};

const allInteractiveLayerIds = [
  "segment-polygons",
  "segment-centerlines",
  "vtti-segment-polygons",
  "gates",
  "vsl",
  "lcs-aggregated",
  "lcs",
  "dms",
  "ddms",
  "mvd-aggregated",
  "psm",
  "wzdx-line",
  "wzdx-multipoint",
];

const mapMessageLayer: LayerProps = {
  id: "mapMessage",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#eb34e8",
  },
};

const connectingLanesLayer: LayerProps = {
  id: "connectingLanes",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#43eb34",
    "line-dasharray": [2, 1],
  },
};

const connectingLanesLayerInactive: LayerProps = {
  id: "connectingLanesInactive",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#545454",
    "line-dasharray": [2, 1],
  },
};

const connectingLanesLayerMissing: LayerProps = {
  id: "connectingLanesMissing",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#000000",
    "line-dasharray": [2, 1],
  },
};

const RED_LIGHT = "#FF0000";
const YELLOW_LIGHT = "#FFFF00";
const GREEN_LIGHT = "#187019";

const signalStateLayerGreen: LayerProps = {
  id: "signalStatesGreen",
  type: "symbol",
  layout: {
    "icon-image": "traffic-light-icon-green-1",
    "icon-allow-overlap": false,
    "icon-size": ["interpolate", ["linear"], ["zoom"], 0, 0, 6, 0.5, 9, 0.4, 22, 0.08],
  },
};

const signalStateLayerRed: LayerProps = {
  id: "signalStatesRed",
  type: "symbol",
  layout: {
    "icon-image": "traffic-light-icon-red-1",
    "icon-allow-overlap": false,
    "icon-size": ["interpolate", ["linear"], ["zoom"], 0, 0, 6, 0.5, 9, 0.4, 22, 0.08],
  },
};

const signalStateLayerYellow: LayerProps = {
  id: "signalStatesYellow",
  type: "symbol",
  layout: {
    "icon-image": "traffic-light-icon-yellow-1",
    "icon-allow-overlap": false,
    "icon-size": ["interpolate", ["linear"], ["zoom"], 0, 0, 6, 0.5, 9, 0.4, 22, 0.08],
  },
};

const bsmLayer: LayerProps = {
  id: "bsm",
  type: "circle",
  paint: {
    "circle-color": "#0000FF",
    "circle-radius": 8,
  },
};

type MyProps = {
  startDate: Date;
  endDate: Date;
  vehicleId?: string;
  displayText: string;
};

const MapTab = (props: MyProps) => {
  const MAPBOX_TOKEN =
    "pk.eyJ1IjoidG9ueWVuZ2xpc2giLCJhIjoiY2tzajQwcDJvMGQ3bjJucW0yaDMxbThwYSJ9.ff26IdP_Y9hiE82AGx_wCg"; //process.env.MAPBOX_TOKEN!;

  const [marks, setMarks] = useState<{ value: number; label: string }[]>([]);
  const [mapData, setMapData] = useState<ProcessedMap>();
  const [mapSignalGroups, setMapSignalGroups] = useState<SignalStateFeatureCollection>();
  const [signalStateData, setSignalStateData] = useState<SignalStateFeatureCollection[]>();
  const [spatSignalGroups, setSpatSignalGroups] = useState<SpatSignalGroups>();
  const [currentSignalGroups, setCurrentSignalGroups] = useState<SpatSignalGroup[]>();
  const [connectingLanes, setCollectingLanes] = useState<ConnectingLanesFeatureCollection>();
  const [bsmData, setBsmData] = useState<BsmFeatureCollection>();
  //   const mapRef = useRef<mapboxgl.Map>();
  const [viewState, setViewState] = React.useState({
    latitude: 39.587905,
    longitude: -105.0907089,
    zoom: 19,
  });
  const [sliderValue, setSliderValue] = React.useState<number>(30);
  const mapRef = React.useRef<any>(null);
  const { intersectionId: dbIntersectionId } = useDashboardContext();

  const parseMapSignalGroups = (mapMessage: ProcessedMap): SignalStateFeatureCollection => {
    const features: SignalStateFeature[] = [];

    mapMessage.mapFeatureCollection.features.forEach((mapFeature: MapFeature) => {
      if (
        !mapFeature.properties.ingressApproach ||
        !mapFeature?.properties?.connectsTo?.[0]?.signalGroup
      ) {
        return;
      }
      features.push({
        type: "Feature",
        properties: {
          signalGroup: mapFeature.properties.connectsTo[0].signalGroup,
          intersectionId: mapMessage.properties.intersectionId,
          color: "#FFFFFF",
        },
        geometry: {
          type: "Point",
          coordinates: mapFeature.geometry.coordinates[0],
        },
      });
    });

    return {
      type: "FeatureCollection",
      features: features,
    };
  };

  const parseSignalStateToColor = (state?: SignalState): string => {
    switch (state) {
      case "STOP_AND_REMAIN":
        return RED_LIGHT; // red
      case "PROTECTED_CLEARANCE":
        return YELLOW_LIGHT; // yellow
      case "PROTECTED_MOVEMENT_ALLOWED":
        return GREEN_LIGHT; // green
      default:
        return "#FFFFFF";
    }
  };

  const parseSpatSignalGroups = (spats: ProcessedSpat[]): SpatSignalGroups => {
    const timedSignalGroups: SpatSignalGroups = {};
    spats.forEach((spat: ProcessedSpat) => {
      timedSignalGroups[Date.parse(spat.odeReceivedAt)] = spat.states.map((state) => {
        return {
          signalGroup: state.signalGroup,
          state: state.stateTimeSpeed?.[0]?.eventState as SignalState,
        };
      });
    });
    return timedSignalGroups;
  };

  const parseBsmToGeojson = (bsmData: OdeBsmData[]): BsmFeatureCollection => {
    return {
      type: "FeatureCollection",
      features: bsmData.map((bsm) => {
        return {
          type: "Feature",
          properties: bsm.payload.data.coreData,
          geometry: {
            type: "Point",
            coordinates: [
              bsm.payload.data.coreData.position.longitude,
              bsm.payload.data.coreData.position.latitude,
            ],
          },
        };
      }),
    };
  };

  const filterConnections = (
    connectingLanes: ConnectingLanesFeatureCollection,
    signalGroups: SpatSignalGroup[],
    active: boolean | null
  ): ConnectingLanesFeatureCollection => {
    return {
      ...connectingLanes,
      features: connectingLanes.features.filter((feature) => {
        const val: boolean =
          signalGroups.find(
            (signalGroup) =>
              signalGroup.signalGroup == feature.properties.signalGroupId &&
              signalGroup.state != "STOP_AND_REMAIN"
          ) !== undefined;
        if (active === null) {
          return feature.properties.signalGroupId === null;
        }
        return active ? val : !val;
      }),
    };
  };

  const generateSignalStateFeatureCollection = (
    prevSignalStates: SignalStateFeatureCollection,
    signalGroups: SpatSignalGroup[]
  ): SignalStateFeatureCollection[] => {
    const red: SignalStateFeatureCollection = { ...prevSignalStates, features: [] };
    const yellow: SignalStateFeatureCollection = { ...prevSignalStates, features: [] };
    const green: SignalStateFeatureCollection = { ...prevSignalStates, features: [] };
    prevSignalStates.features.forEach((feature) => {
      feature.properties.color = parseSignalStateToColor(
        signalGroups?.find(
          (signalGroup) => signalGroup.signalGroup == feature.properties.signalGroup
        )?.state
      );
      if (feature.properties.color == RED_LIGHT) red.features.push(feature);
      if (feature.properties.color == YELLOW_LIGHT) yellow.features.push(feature);
      if (feature.properties.color == GREEN_LIGHT) green.features.push(feature);
    });
    return [red, yellow, green];
  };

  //   useEffect(() => {
  //     setPointData(mapMessageData.mapFeatureCollection)
  //   }, []);

  const pullInitialData = async () => {
    const mapMessage: ProcessedMap = (
      await MessageMonitorApi.getMapMessages({
        token: "token",
        intersection_id: dbIntersectionId,
        startTime: props.startDate,
        endTime: props.endDate,
        latest: true,
      })
    ).at(-1)!;
    const mapSignalGroupsLocal = parseMapSignalGroups(mapMessage);
    setMapData(mapMessage);
    setMapSignalGroups(mapSignalGroupsLocal);

    setCollectingLanes(mapMessage.connectingLanesFeatureCollection);

    const spatSignalGroupsLocal = parseSpatSignalGroups(
      await MessageMonitorApi.getSpatMessages({
        token: "token",
        intersection_id: dbIntersectionId,
        startTime: props.startDate,
        endTime: props.endDate,
      })
    );
    setSpatSignalGroups(spatSignalGroupsLocal);
    setSliderValue(Number(Object.keys(spatSignalGroupsLocal)?.[0] ?? 0));

    MessageMonitorApi.getBsmMessages({
      token: "token",
      vehicleId: props.vehicleId,
      startTime: props.startDate,
      endTime: props.endDate,
    }).then((bsmData) => setBsmData(parseBsmToGeojson(bsmData)));
  };

  useEffect(() => {
    pullInitialData();
  }, []);

  useEffect(() => {
    if (!mapSignalGroups || !spatSignalGroups) {
      console.log("RETURNING");
      console.log(mapSignalGroups, spatSignalGroups);
      return;
    }
    const keys: string[] = Object.keys(spatSignalGroups ?? {});
    const signalGroupsKey = Number(keys[sliderValue]);
    const currentSignalGroupsLocal = spatSignalGroups?.[signalGroupsKey];
    setCurrentSignalGroups(currentSignalGroupsLocal);
    setSignalStateData(
      generateSignalStateFeatureCollection(mapSignalGroups, spatSignalGroups?.[signalGroupsKey])
    );
    const marksLocal: { value: number; label: string }[] = [];
    for (let i = 0; i < keys.length; i += parseInt((keys.length / 5).toString())) {
      marksLocal.push({ value: i, label: new Date(Number(keys[i])).toISOString() });
    }
    setMarks(marksLocal);
  }, [mapSignalGroups, sliderValue, spatSignalGroups]);

  const getSignalGroups = () => {};

  const handleSliderChange = (event: Event, newValue: number | number[]) => {
    setSliderValue(newValue as number);
  };

  return (
    <Container fluid={true} style={{ width: "100%", height: "100%", display: "flex" }}>
      <Col className="mapContainer" style={{ overflow: "hidden" }}>
        <div
          style={{
            padding: "0px 0px 6px 12px",
            marginTop: "6px",
            marginLeft: "35px",
            position: "absolute",
            zIndex: 10,
            top: 0,
            left: 0,
            width: 1500,
            borderRadius: "4px",
            fontSize: "16px",
            maxHeight: "calc(100vh - 120px)",
            overflow: "auto",
            scrollBehavior: "auto",
          }}
        >
          <Box style={{ position: "relative" }}>
            <Paper sx={{ pt: 1, pb: 1, opacity: 0.85 }}>
              <ControlPanel
                sx={{ flex: 0 }}
                slider={sliderValue}
                setSlider={handleSliderChange}
                marks={marks}
              />
            </Paper>
          </Box>
        </div>

        <Map
          {...viewState}
          ref={mapRef}
          onLoad={() => {
            // const image = new Image(35, 35);
            // image.src = "./icons/traffic-light-green.svg";
            // mapRef.current.addImage("traffic_light_icon_green", image);
            // mapRef.current.loadImage(traffic_light_icon_green, (error, image) => {
            //   if (error) throw error;
            // });
          }}
          //   mapStyle="mapbox://styles/mapbox/satellite-streets-v12"
          mapStyle="mapbox://styles/tonyenglish/cld2bdrk3000201qmx2jb95kf"
          mapboxAccessToken={MAPBOX_TOKEN}
          attributionControl={true}
          customAttribution={['<a href="https://www.cotrip.com/" target="_blank">© CDOT</a>']}
          styleDiffing
          style={{ width: "100%", height: "100%" }}
          onMove={(evt) => setViewState(evt.viewState)}
          // onClick={this.onClickMap}
          // onMouseDown={this.onMouseDown}
          // onMouseUp={this.onMouseUp}
          // interactiveLayerIds={interactiveLayerIds}
          // cursor={cursor}
          // onMouseEnter={() => this.setState({ cursor: "pointer" })}
          // onMouseLeave={() => this.setState({ cursor: "grab" })}
        >
          {/* {mapData && (
            <Source type="geojson" data={mapData?.connectingLanesFeatureCollection}>
              <Layer {...connectingLanesLayer} />
            </Source>
          )} */}
          {connectingLanes && currentSignalGroups && (
            <Source
              type="geojson"
              data={filterConnections(connectingLanes, currentSignalGroups, true)}
            >
              <Layer {...connectingLanesLayer} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && (
            <Source
              type="geojson"
              data={filterConnections(connectingLanes, currentSignalGroups, false)}
            >
              <Layer {...connectingLanesLayerInactive} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && (
            <Source
              type="geojson"
              data={filterConnections(connectingLanes, currentSignalGroups, null)}
            >
              <Layer {...connectingLanesLayerMissing} />
            </Source>
          )}
          {bsmData && (
            <Source type="geojson" data={bsmData}>
              <Layer {...bsmLayer} />
            </Source>
          )}
          {mapData && (
            <Source type="geojson" data={mapData?.mapFeatureCollection}>
              <Layer {...mapMessageLayer} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && signalStateData && (
            <Source type="geojson" data={signalStateData[0]}>
              <Layer {...signalStateLayerRed} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && signalStateData && (
            <Source type="geojson" data={signalStateData[1]}>
              <Layer {...signalStateLayerYellow} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && signalStateData && (
            <Source type="geojson" data={signalStateData[2]}>
              <Layer {...signalStateLayerGreen} />
            </Source>
          )}
        </Map>
      </Col>
      <div>{}</div>
    </Container>
  );
};

export default MapTab;
