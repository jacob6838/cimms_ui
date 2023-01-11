
type TimeChangeDetailsEvent = {
  roadRegulatorID: number
  intersectionID: number
  signalGroup: number
  firstSpatTimestamp: number
  secondSpatTimestamp: number
  firstTimeMarkType: number
  secondTimeMarkType: number
  firstConflictingTimemark: number
  secondConflictingTimemark: number
};