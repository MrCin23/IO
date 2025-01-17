export interface Resource {
  resourceId: number;
  resourceName: string;
  resourceType: string;
  resourceQuantity: number;
  resourceStatus: string;
  warehouseId: number;
}

export interface Warehouse {
  warehouseId: number;
  warehouseName: string;
  location: string;
}

export interface Message {
    senderName: string;
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