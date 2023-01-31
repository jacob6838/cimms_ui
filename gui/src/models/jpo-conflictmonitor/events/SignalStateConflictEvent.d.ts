
type SignalStateConflictEvent = Event & {
  timestamp: number
  roadRegulatorID: number
  intersectionID: number
  conflictType: J2735MovementPhaseState
  firstConflictingSignalGroup: number
  firstConflictingSignalState: J2735MovementPhaseState
  secondConflictingSignalGroup: number
  secondConflictingSignalState: J2735MovementPhaseState
};