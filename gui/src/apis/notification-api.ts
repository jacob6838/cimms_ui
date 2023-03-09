import { authApiHelper } from "./api-helper";

class NotificationApi {
  getNotifications(): MessageMonitor.Notification[] {
    return notifications;
  }

  //   getCeaseBroadcastRecommendations(): CeaseBroadcastRecommendation[] {
  //     return ceaseBroadcastRecommendations;
  //   }
}

export default new NotificationApi();

const notifications: MessageMonitor.Notification[] = [
  {
    notificationGeneratedAt: new Date(),
    notificationText:
      "SPAT Broadcast rate of 20 messages per hour is lower than the threshold of 50/hour",
    notificationType: "SpatBroadcastRateNotification",
    notificationDate: new Date(),
    id: "1",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "MAP and SPAT messages do not align for intersection",
    notificationType: "IntersectionReferenceAlignmentNotification",
    notificationDate: new Date(),
    id: "2",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "A signal group alignment event was generated",
    notificationType: "SignalGroupAlignmentNotification",
    notificationDate: new Date(),
    id: "3",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "SPAT messages do not contain the minimum required data",
    notificationType: "SpatMinimumDataNotification",
    notificationDate: new Date(),
    id: "4",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "MAP messages do not contain the minimum required data",
    notificationType: "MapMinimumDataNotification",
    notificationDate: new Date(),
    id: "5",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText:
      "MAP message broadcast rate of 15 messages per hour is lower than the threshold of 50/hour",
    notificationType: "MapBroadcastRateNotification",
    notificationDate: new Date(),
    id: "6",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "Percentage of stop-And-Remain assessed events exceeds the threshold of 10%",
    notificationType: "SignalStateAssessmentNotification",
    notificationDate: new Date(),
    id: "7",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText:
      "The lane direction of travel assessment found the median distance from centerline to be greater than the threshold of 0.25 meters",
    notificationType: "LaneDirectionOfTravelAssessmentNotification",
    notificationDate: new Date(),
    id: "8",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "MAP message contains 5 ingress-egress pairs with no connections",
    notificationType: "ConnectionOfTravelNotification",
    notificationDate: new Date(),
    id: "9",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "The signal state conflict algorithm found 2 conflicts",
    notificationType: "SignalStateConflictNotification",
    notificationDate: new Date(),
    id: "10",
  },
  {
    notificationGeneratedAt: new Date(),
    notificationText: "The time change details algorithm found 2 invalid time changes",
    notificationType: "TimeChangeDetailsNotification",
    notificationDate: new Date(),
    id: "11",
  },
];
