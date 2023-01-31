
type SignalGroupAlignmentEvent = Event & {
    sourceID: string;
    timestamp: number;
    spatSignalGroupIds: Set<Integer>;
    mapSignalGroupIds: Set<Integer>;
};