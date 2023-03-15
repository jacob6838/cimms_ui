import React, { useState, useEffect } from "react";
import Map, { Source, Layer } from "react-map-gl";

import { Container, Col } from "reactstrap";

import { Paper, Box } from "@mui/material";

// import mapMessageData from "./processed_map_v4.json";
import type { LayerProps } from "react-map-gl";
import ControlPanel from "./control-panel";
import MessageMonitorApi from "../../apis/mm-api";
import { useDashboardContext } from "../../contexts/dashboard-context";
import { Marker } from "mapbox-gl";

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
    "line-color": "#30af25",
    "line-dasharray": [2, 1],
  },
};

const connectingLanesLayerYellow: LayerProps = {
  id: "connectingLanesYellow",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#c5b800",
    "line-dasharray": [2, 1],
  },
};

const connectingLanesLayerInactive: LayerProps = {
  id: "connectingLanesInactive",
  type: "line",
  paint: {
    "line-width": 5,
    "line-color": "#da2f2f",
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
    "icon-allow-overlap": true,
    "icon-size": ["interpolate", ["linear"], ["zoom"], 0, 0, 6, 0.5, 9, 0.4, 22, 0.08],
  },
};

const signalStateLayerRed: LayerProps = {
  id: "signalStatesRed",
  type: "symbol",
  layout: {
    "icon-image": "traffic-light-icon-red-1",
    "icon-allow-overlap": true,
    "icon-size": ["interpolate", ["linear"], ["zoom"], 0, 0, 6, 0.5, 9, 0.4, 22, 0.08],
  },
};

const signalStateLayerYellow: LayerProps = {
  id: "signalStatesYellow",
  type: "symbol",
  layout: {
    "icon-image": "traffic-light-icon-yellow-1",
    "icon-allow-overlap": true,
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

const markerLayer: LayerProps = {
  id: "invalidLaneCollection",
  type: "line",
  paint: {
    "line-width": 20,
    "line-color": "#da2f2f",
    "line-dasharray": [2, 1],
  },
};

type MyProps = {
  startDate: Date;
  endDate: Date;
  eventDate: Date;
  vehicleId?: string;
  displayText: string;
};

const MapTab = (props: MyProps) => {
  const MAPBOX_TOKEN =
    "pk.eyJ1IjoidG9ueWVuZ2xpc2giLCJhIjoiY2tzajQwcDJvMGQ3bjJucW0yaDMxbThwYSJ9.ff26IdP_Y9hiE82AGx_wCg"; //process.env.MAPBOX_TOKEN!;

  const [queryParams, setQueryParams] = useState<{
    startDate: Date;
    endDate: Date;
    eventDate: Date;
    vehicleId?: string;
    timeWindowSeconds: number;
  }>({ ...props, timeWindowSeconds: 60 });
  const [mapData, setMapData] = useState<ProcessedMap>();
  const [mapSignalGroups, setMapSignalGroups] = useState<SignalStateFeatureCollection>();
  const [signalStateData, setSignalStateData] = useState<SignalStateFeatureCollection[]>();
  const [spatSignalGroups, setSpatSignalGroups] = useState<SpatSignalGroups>();
  const [currentSignalGroups, setCurrentSignalGroups] = useState<SpatSignalGroup[]>();
  const [currentBsms, setCurrentBsms] = useState<BsmFeatureCollection>({
    type: "FeatureCollection",
    features: [],
  });
  const [connectingLanes, setConnectingLanes] = useState<ConnectingLanesFeatureCollection>();
  const [bsmData, setBsmData] = useState<BsmFeatureCollection>({
    type: "FeatureCollection",
    features: [],
  });
  //   const mapRef = useRef<mapboxgl.Map>();
  const [viewState, setViewState] = React.useState({
    latitude: 39.587905,
    longitude: -105.0907089,
    zoom: 19,
  });
  const [sliderValue, setSliderValue] = React.useState<number>(0);
  const [renderTimeInterval, setRenderTimeInterval] = React.useState<number[]>([0, 0]);
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

  const createMarkerForNotification = (
    notification: MessageMonitor.Notification,
    connectingLanes: ConnectingLanesFeatureCollection
  ) => {
    if (!connectingLanes) return;
    const markerCollection = { type: "FeatureCollection", features: [] };
    switch (notification.notificationType) {
      case "ConnectionOfTravelNotification":
        console.log("CONNECTION OF TRAVEL");
        const notificationVal = notification as ConnectionOfTravelNotification;
        const assessmentGroups = notificationVal.assessment.connectionOfTravelAssessment;
        assessmentGroups.forEach((assessmentGroup) => {
          const ingressLocation: number[] | undefined = connectingLanes.features.find(
            (connectingLaneFeature: ConnectingLanesFeature) => {
              return (
                connectingLaneFeature.properties.ingressLaneId === assessmentGroup.ingressLaneID
              );
            }
          )?.geometry.coordinates[0];
          const egressLocation: number[] | undefined = connectingLanes.features.find(
            (connectingLaneFeature: ConnectingLanesFeature) => {
              return connectingLaneFeature.properties.egressLaneId === assessmentGroup.egressLaneID;
            }
          )?.geometry.coordinates[0];
          console.log("Location", location, connectingLanes);
          if (!ingressLocation || !egressLocation) return;
          const marker = {
            type: "Feature",
            properties: {
              description: `${notificationVal.notificationText}, egress lane ${assessmentGroup.egressLaneID}, incress lane ${assessmentGroup.ingressLaneID}, connection ID ${assessmentGroup.connectionID}, event count ${assessmentGroup.eventCount}`,
              title: notificationVal.notificationType,
            },
            geometry: {
              type: "LineString",
              coordinates: [ingressLocation, egressLocation],
            },
          };
          markerCollection.features.push(marker);
        });
    }
    return markerCollection;
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
          properties: {
            ...bsm.payload.data.coreData,
            odeReceivedAt: new Date(bsm.metadata.odeReceivedAt).getTime() / 1000,
          },
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
    state: SignalState | null
  ): ConnectingLanesFeatureCollection => {
    return {
      ...connectingLanes,
      features: connectingLanes.features.filter((feature) => {
        const val: boolean =
          signalGroups.find(
            (signalGroup) =>
              signalGroup.signalGroup == feature.properties.signalGroupId &&
              signalGroup.state != state
          ) !== undefined;
        return !val;
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
    console.log(queryParams);
    const mapMessages: ProcessedMap[] = await MessageMonitorApi.getMapMessages({
      token: "token",
      intersection_id: dbIntersectionId,
      startTime: new Date(queryParams.startDate.getTime() - 1000 * 60 * 60 * 1),
      endTime: queryParams.endDate,
      latest: true,
    });
    console.log(mapMessages);
    if (!mapMessages || mapMessages.length == 0) {
      console.log("NO MAP MESSAGES WITHIN TIME");
      return;
    }
    const latestMapMessage: ProcessedMap = mapMessages.at(-1)!;
    const mapSignalGroupsLocal = parseMapSignalGroups(latestMapMessage);
    setMapData(latestMapMessage);
    setMapSignalGroups(mapSignalGroupsLocal);

    setConnectingLanes(latestMapMessage.connectingLanesFeatureCollection);

    const spatSignalGroupsLocal = parseSpatSignalGroups(
      await MessageMonitorApi.getSpatMessages({
        token: "token",
        intersection_id: dbIntersectionId,
        startTime: queryParams.startDate,
        endTime: queryParams.endDate,
      })
    );

    setSpatSignalGroups(spatSignalGroupsLocal);

    setBsmData(
      parseBsmToGeojson(
        await MessageMonitorApi.getBsmMessages({
          token: "token",
          vehicleId: queryParams.vehicleId,
          startTime: queryParams.startDate,
          endTime: queryParams.endDate,
        })
      )
    );

    setSliderValue(
      Math.min(
        getTimeRange(queryParams.startDate, queryParams.eventDate ?? new Date()),
        getTimeRange(queryParams.startDate, queryParams.endDate)
      )
    );
  };

  useEffect(() => {
    pullInitialData();
  }, [queryParams]);

  useEffect(() => {
    console.log("SLIDER VALUE", sliderValue);
  }, [sliderValue]);

  useEffect(() => {
    if (!mapSignalGroups || !spatSignalGroups) {
      console.log("RETURNING");
      console.log(mapSignalGroups, spatSignalGroups);
      return;
    }

    // retrieve filtered SPATs
    let closestSignalGroup: { spat: SpatSignalGroup[]; datetime: number } | null = null;
    for (const datetime in spatSignalGroups) {
      const datetimeNum = Number(datetime) / 1000;
      if (datetimeNum >= renderTimeInterval[0] && datetimeNum <= renderTimeInterval[1]) {
        if (
          closestSignalGroup === null ||
          Math.abs(datetimeNum - renderTimeInterval[0]) <
            Math.abs(closestSignalGroup.datetime - renderTimeInterval[0])
        ) {
          closestSignalGroup = { datetime: datetimeNum, spat: spatSignalGroups[datetime] };
        }
      }
    }
    if (closestSignalGroup !== null) {
      setCurrentSignalGroups(closestSignalGroup.spat);
      setSignalStateData(
        generateSignalStateFeatureCollection(mapSignalGroups, closestSignalGroup.spat)
      );
    } else {
      setCurrentSignalGroups(undefined);
      setSignalStateData(undefined);
    }

    console.log("FILTERING ALL DATA", bsmData);

    // retrieve filtered BSMs
    const filteredBsms: BsmFeature[] = [];
    (bsmData?.features ?? []).forEach((feature) => {
      if (
        feature.properties?.odeReceivedAt >= renderTimeInterval[0] &&
        feature.properties?.odeReceivedAt <= renderTimeInterval[1]
      ) {
        filteredBsms.push(feature);
      }
    });

    setCurrentBsms({ ...bsmData, features: filteredBsms });
  }, [mapSignalGroups, renderTimeInterval, spatSignalGroups]);

  useEffect(() => {
    const startTime = queryParams.startDate.getTime() / 1000;
    const timeRange = getTimeRange(queryParams.startDate, queryParams.endDate);
    console.log(timeRange, sliderValue);

    const filteredStartTime = startTime + sliderValue;
    const filteredEndTime = startTime + sliderValue + queryParams.timeWindowSeconds;

    setRenderTimeInterval([filteredStartTime, filteredEndTime]);
  }, [sliderValue, queryParams]);

  const getTimeRange = (startDate: Date, endDate: Date) => {
    return (endDate.getTime() - startDate.getTime()) / 1000;
  };

  const getSignalGroups = () => {};

  const handleSliderChange = (event: Event, newValue: number | number[]) => {
    setSliderValue(newValue as number);
  };

  const downloadJsonFile = (contents: any, name: string) => {
    const element = document.createElement("a");
    const file = new Blob([JSON.stringify(contents)], { type: "text/plain" });
    element.href = URL.createObjectURL(file);
    element.download = `${name}.json`;
    document.body.appendChild(element); // Required for this to work in FireFox
    element.click();
  };

  const downloadAllData = () => {
    const fileName = `intersection_${dbIntersectionId}_data`;
    const data = {
      mapData: mapData,
      spatSignalGroups: spatSignalGroups,
      mapSignalGroups: mapSignalGroups,
      bsmData: bsmData,
    };
    downloadJsonFile(data, fileName);
  };

  const onTimeQueryChanged = (
    eventTime: Date = new Date(),
    timeBefore?: number,
    timeAfter?: number,
    timeWindowSeconds?: number
  ) => {
    console.log("UPDATING QUERY PARAMS", eventTime, timeBefore, timeAfter, timeWindowSeconds);
    setQueryParams((prevState) => {
      return {
        startDate: new Date(eventTime.getTime() - (timeBefore ?? 0) * 1000),
        endDate: new Date(eventTime.getTime() + (timeAfter ?? 0) * 1000),
        eventDate: eventTime,
        timeWindowSeconds: timeWindowSeconds ?? prevState.timeWindowSeconds,
      };
    });
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
                sliderValue={sliderValue}
                setSlider={handleSliderChange}
                downloadAllData={downloadAllData}
                timeQueryParams={queryParams}
                onTimeQueryChanged={onTimeQueryChanged}
                max={getTimeRange(queryParams.startDate, queryParams.endDate)}
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
              data={filterConnections(
                connectingLanes,
                currentSignalGroups,
                "PROTECTED_MOVEMENT_ALLOWED"
              )}
            >
              <Layer {...connectingLanesLayer} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && (
            <Source
              type="geojson"
              data={filterConnections(connectingLanes, currentSignalGroups, "PROTECTED_CLEARANCE")}
            >
              <Layer {...connectingLanesLayerYellow} />
            </Source>
          )}
          {connectingLanes && currentSignalGroups && (
            <Source
              type="geojson"
              data={filterConnections(connectingLanes, currentSignalGroups, "STOP_AND_REMAIN")}
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
          {currentBsms && (
            <Source type="geojson" data={currentBsms}>
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
          {/* {connectingLanes && (
            <Source
              type="geojson"
              data={createMarkerForNotification(
                {
                  notificationGeneratedAt: 1678491340046,
                  assessment: {
                    assessmentGeneratedAt: 1678491339928,
                    assessmentType: "ConnectionOfTravel",
                    connectionOfTravelAssessment: [
                      {
                        egressLaneID: 5,
                        ingressLaneID: 3,
                        connectionID: -1,
                        eventCount: 1,
                      },
                    ],
                    timestamp: 1678491339931,
                  },
                  notificationText:
                    "Connection of Travel Notification, Unknown Lane connection between ingress lane 3and egress Lane 5.",
                  notificationHeading: "Connection of Travel Notification",
                  notificationType: "ConnectionOfTravelNotification",
                },
                connectingLanes
              )}
            >
              <Layer {...markerLayer} />
            </Source>
          )} */}
        </Map>
      </Col>
      <div>{}</div>
    </Container>
  );
};

export default MapTab;
