export interface Message {
    sender: string;
    receiver: string;
    content: string;
    timestamp?: Date;
}

export interface ChatDB {
    id: string;
    users: string[];
    name: string;
}