import React, { useRef, useState, useEffect } from "react";
import Map, { Source, Layer, useMap } from "react-map-gl";

import { Container, Col } from "reactstrap";

import mapboxgl from "mapbox-gl";
import mapMessageData from "./processed_map_v4.json";
import type { LayerProps } from "react-map-gl";
import ControlPanel from "./control-panel";
import MessageMonitorApi from "../../apis/mm-api";

const MAPBOX_TOKEN =
  "pk.eyJ1IjoidG9ueWVuZ2xpc2giLCJhIjoiY2tzajQwcDJvMGQ3bjJucW0yaDMxbThwYSJ9.ff26IdP_Y9hiE82AGx_wCg";

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
    "line-color": "#000000",
  },
};

const RED_LIGHT = "#FF0000";
const YELLOW_LIGHT = "#FFFF00";
const GREEN_LIGHT = "#00FF00";

const signalStateLayerGreen: LayerProps = {
  id: "signalStatesGreen",
  type: "circle",
  paint: {
    "circle-color": GREEN_LIGHT,
    "circle-radius": 12,
  },
};

const signalStateLayerRed: LayerProps = {
  id: "signalStatesRed",
  type: "circle",
  paint: {
    "circle-color": RED_LIGHT,
    "circle-radius": 12,
  },
};

const signalStateLayerYellow: LayerProps = {
  id: "signalStatesYellow",
  type: "circle",
  paint: {
    "circle-color": YELLOW_LIGHT,
    "circle-radius": 12,
  },
};

const bsmLayer: LayerProps = {
  id: "bsm",
  type: "circle",
  paint: {
    "circle-color": "#0000FF",
    "circle-radius": 6,
  },
};

type MyProps = {};

const MapTab = (props: MyProps) => {
  const [pointData, setPointData] = useState<any>(null);
  const [marks, setMarks] = useState<{ value: number; label: string }[]>([]);
  const [mapData, setMapData] = useState<ProcessedMap>();
  const [mapSignalGroups, setMapSignalGroups] = useState<SignalStateFeatureCollection>();
  const [signalStateData, setSignalStateData] = useState<SignalStateFeatureCollection[]>();
  const [spatSignalGroups, setSpatSignalGroups] = useState<SpatSignalGroups>();
  const [bsmData, setBsmData] = useState<BsmFeatureCollection>();
  const mapRef = useRef<mapboxgl.Map>();
  const [viewState, setViewState] = React.useState({
    latitude: 39.587905,
    longitude: -105.0907089,
    zoom: 19,
  });
  const [sliderValue, setSliderValue] = React.useState<number>(30);

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

  useEffect(() => {
    setPointData(mapMessageData.mapFeatureCollection);

    const mapMessage: ProcessedMap = MessageMonitorApi.getMapMessage();
    const mapSignalGroupsLocal = parseMapSignalGroups(mapMessage);
    setMapData(mapMessage);
    setMapSignalGroups(mapSignalGroupsLocal);

    const spatSignalGroupsLocal = parseSpatSignalGroups(MessageMonitorApi.getSpatMessages());
    setSpatSignalGroups(spatSignalGroupsLocal);
    setSliderValue(Number(Object.keys(spatSignalGroupsLocal)?.[0] ?? 0));

    setBsmData(parseBsmToGeojson(MessageMonitorApi.getBsmMessages()));
  }, []);

  useEffect(() => {
    if (!mapSignalGroups || !spatSignalGroups) {
      return;
    }
    const keys: string[] = Object.keys(spatSignalGroups ?? {});
    const signalGroupsKey = Number(keys[sliderValue]);
    setSignalStateData(
      generateSignalStateFeatureCollection(mapSignalGroups, spatSignalGroups?.[signalGroupsKey])
    );
    const marksLocal: { value: number; label: string }[] = [];
    for (let i = 0; i < keys.length; i += parseInt((keys.length / 5).toString())) {
      marksLocal.push({ value: i, label: new Date(Number(keys[i])).toISOString() });
    }
    setMarks(marksLocal);
  }, [sliderValue, mapSignalGroups, spatSignalGroups]);

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
            borderRadius: "4px",
            fontSize: "16px",
            maxHeight: "calc(100vh - 120px)",
            overflow: "auto",
            scrollBehavior: "auto",
          }}
        ></div>

        <ControlPanel
          sx={{ flex: 0 }}
          slider={sliderValue}
          setSlider={handleSliderChange}
          marks={marks}
        />
        <Map
          {...viewState}
          // ref={mapRef}
          mapStyle="mapbox://styles/mapbox/satellite-streets-v12"
          mapboxAccessToken={MAPBOX_TOKEN}
          attributionControl={true}
          customAttribution={[
            '<a href="https://www.transurban.com/" target="_blank">© Transurban</a>',
            '<a href="https://www.vtti.vt.edu/" target="_blank">© VTTI</a>',
            '<a href="https://www.tomtom.com/" target="_blank">© TomTom</a>',
          ]}
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
          {signalStateData && (
            <Source type="geojson" data={signalStateData[0]}>
              <Layer {...signalStateLayerRed} />
            </Source>
          )}
          {signalStateData && (
            <Source type="geojson" data={signalStateData[1]}>
              <Layer {...signalStateLayerYellow} />
            </Source>
          )}
          {signalStateData && (
            <Source type="geojson" data={signalStateData[2]}>
              <Layer {...signalStateLayerGreen} />
            </Source>
          )}
        </Map>
      </Col>
    </Container>
  );
};

export default MapTab;
