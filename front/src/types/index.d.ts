export interface Message {
    senderId: number;
    chatId: number;
    content: string;
    timestamp?: Date;
}