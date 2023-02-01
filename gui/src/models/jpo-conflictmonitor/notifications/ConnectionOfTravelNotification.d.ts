/// <reference path="Notification.d.ts" />
type ConnectionOfTravelNotification = MessageMonitor.Notification  & {
    assessmentStartTime: Date,
    assessmentEndTime: Date,
    assessmentResult: string,
    invalidAssessmentData: string,
}