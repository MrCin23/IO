export interface Message {
    senderId: number;
    chatId: number;
    content: string;
    timestamp?: Date;
}

export interface ChatDB {
    id: string;
    users: string[];
    name: string;
}