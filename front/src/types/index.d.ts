export interface Message {
    sender: string;
    receiver: string;
    content: string;
    timestamp?: Date;
}