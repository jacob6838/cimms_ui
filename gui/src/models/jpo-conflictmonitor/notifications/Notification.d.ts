declare namespace MessageMonitor {
type Notification = {
  Logger: any
  notificationGeneratedAt: number
  notificationType: str
  notificationText: str
  notificationHeading: str
  notificationExpiresAt: Date
  String: abstract
}
}