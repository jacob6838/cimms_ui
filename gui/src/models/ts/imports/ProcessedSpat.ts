type ProcessedSpat = {
    messageType: string,
    odeReceivedAt: string,
    originIp: string,
    name: string,
    region: number,
    intersectionId: number,
    cti4501Conformant: boolean,
    validationMessages: ProcessedValidationMessage[],
    revision: number,
    status: IntersectionStatusObject,
    utcTimeStamp: Date,
    enabledLanes: number[],
    states: MovementState[],
}

type ProcessedValidationMessage = {
    message: string,
    jsonPath: string,
    schemaPath: string,
    exception: string,
}

type IntersectionStatusObject = {
    manualControlIsEnabled: boolean,
    stopTimeIsActivated: boolean,
    failureFlash: boolean,
    preemptIsActive: boolean,
    signalPriorityIsActive: boolean,
    fixedTimeOperation: boolean,
    trafficDependentOperation: boolean,
    standbyOperation: boolean,
    failureMode: boolean,
    off: boolean,
    recentMAPmessageUpdate: boolean,
    recentChangeInMAPassignedLanesIDsUsed: boolean,
    noValidMAPisAvailableAtThisTime: boolean,
    noValidSPATisAvailableAtThisTime: boolean,
}

type MovementState = {
    movementName: string,
    signalGroup: number,
    stateTimeSpeed: MovementEvent[],
}

type MovementEvent = {
    eventState: J2735MovementPhaseState,
    timing: TimingChangeDetails,
    speeds: J2735AdvisorySpeedList,
}

type TimingChangeDetails = {
    startTime: Date,
    minEndTime: Date,
    maxEndTime: Date,
    likelyTime: Date,
    confidence: number,
    nextTime: Date,
}