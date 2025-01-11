export interface Message {
    senderId: string;
    groupId: string;
    content: string;
    timestamp?: Date;
}