type SignalStateEvent = MessageMonitor.Event & {
  timestamp: number;
  roadRegulatorID: number;
  ingressLane: number;
  egressLane: number;
  connectionID: number;
  eventState: J2735MovementPhaseState;
  vehicleID: str;
  latitude: number;
  longitude: number;
  heading: number;
  speed: number;
  signalGroup: number;
};
