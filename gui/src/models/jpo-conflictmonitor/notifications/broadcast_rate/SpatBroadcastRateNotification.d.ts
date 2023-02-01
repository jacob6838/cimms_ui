
type SpatBroadcastRateNotification = BroadcastRateNotification<SpatBroadcastRateEvent>  & {
    message: string,
    date: Date,
}