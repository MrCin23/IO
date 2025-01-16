


export enum NotificationType {
    INFORMATION = "INFORMATION",
    ERROR = "ERROR",
    WARNING = "WARNING",
  }

export default interface Notification{
    message:string;
    type:NotificationType,
    id:number,
    read:boolean
}