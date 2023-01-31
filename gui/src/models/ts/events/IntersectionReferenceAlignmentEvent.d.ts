
type IntersectionReferenceAlignmentEvent = Event & Event & {
  sourceID: str
  timestamp: number
  spatRoadRegulatorIds: Set<Integer>
  mapRoadRegulatorIds: Set<Integer>
  spatIntersectionIds: Set<Integer>
  mapIntersectionIds: Set<Integer>
};