type ConnectionOfTravelEvent = MessageMonitor.Event & {
  timestamp: number;
  roadRegulatorId: number;
  intersectionId: number;
  ingressLaneId: number;
  egressLaneId: number;
  connectionId: number;
};
