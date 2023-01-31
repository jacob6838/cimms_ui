
type LaneDirectionOfTravelEvent = Event & {
  timestamp: number
  roadRegulatorID: number
  intersectionID: number
  laneID: number
  laneSegmentNumber: number
  laneSegmentInitialLatitude: number
  laneSegmentInitialLongitude: number
  laneSegmentFinalLatitude: number
  laneSegmentFinalLongitude: number
  expectedHeading: number
  medianVehicleHeading: number
  medianDistanceFromCenterline: number
  aggregateBSMCount: number
};