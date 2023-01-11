type LaneConnection = {
  referencePoint: OdePosition3D
  ingress: J2735GenericLane
  egress: J2735GenericLane
  ingressPath: LineString
  connectingPath: LineString
  egressPath: LineString
  geometryFactory: GeometryFactory
  signalGroup: number
  interpolationPoints: number
};