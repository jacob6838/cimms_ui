type ProcessedMap = {
    properties: MapSharedProperties,
    mapFeatureCollection: MapFeatureCollection,
    connectingLanesFeatureCollection: ConnectingLanesFeatureCollection,
}

type MapSharedProperties = {
    messageType: string,
    odeReceivedAt: Date,
    originIp: string,
    intersectionName: string,
    region: number,
    intersectionId: number,
    msgIssueRevision: number,
    revision: number,
    refPoint: OdePosition3D,
    cti4501Conformant: Boolean,
    validationMessages: ProcessedValidationMessage[],
    laneWidth: number,
    speedLimits: J2735RegulatorySpeedLimit[],
    mapSource: MapSource, //import us.dot.its.jpo.ode.model.OdeMapMetadata.MapSource;
    timeStamp: Date,
}

type MapSource = 
    "RSU" |
    "V2X" |
    "MMITSS" |
    "unknown";

type MapFeatureCollection = MapFeature[];

type MapFeature = {
    id: number,
    geometry: number[][],
    properties: MapProperties
}

type MapProperties = {
    nodes: MapNode[],
    laneId: number,
    laneName: string,
    sharedWith: J2735BitString,
    egressApproach: number,
    ingressApproach: number,
    ingressPath: boolean,
    egressPath: boolean,
    maneuvers: J2735BitString,
    connectsTo: J2735Connection[],
}

type MapNode = {
    delta: number[],
    dWidth: number,
    dElevation: number,
    stopLine: boolean,
}

type ConnectingLanesFeatureCollection = ConnectingLanesFeature[];

type ConnectingLanesFeature = {
    id: number,
    geometry: number[][],
    properties: ConnectingLanesProperties
}

type ConnectingLanesProperties = {
    signalGroupId: number,
    ingressLaneId: number,
    egressLaneId: number,
}