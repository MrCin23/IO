
export enum AnnouncementType {
    INFORMATION = "INFORMATION",
    WARNING = "WARNING",
  }


export interface createAnnouncement{
  message:string;
  title:string;
  type:AnnouncementType
}

export default interface Announcement{
    message:string;
    type:AnnouncementType,
    title:string,
    id:number
}
