
type ConnectionOfTravelEvent = Event & {
  timestamp: number
  roadRegulatorId: number
  intersectionId: number
  ingressLaneId: number
  egressLaneId: number
  connectionId: number
}