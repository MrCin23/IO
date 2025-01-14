export type FinancialNeed = {
    id: number;
    userId: number;
    mapPointId: number | null;
    description: string;
    creationDate: string;
    expirationDate: string | null;
    status: "IN_PROGRESS" | "PENDING" | "COMPLETED" | "CANCELLED";
    priority: number;
    collectionStatus: number;
    collectionGoal: number;
};

export type ManualNeed = {
    id: number;
    userId: number;
    mapPointId: number;
    description: string;
    creationDate: string;
    expirationDate: string;
    status: "IN_PROGRESS" | "PENDING" | "COMPLETED" | "CANCELLED";
    priority: number;
    volunteers: number;
    maxVolunteers: number;
};

export type MaterialNeed = {
    id: number;
    userId: number;
    mapPointId: number;
    description: string;
    creationDate: string;
    expirationDate: string;
    status: "IN_PROGRESS" | "PENDING" | "COMPLETED" | "CANCELLED";
    priority: number;
    itemCategory: string;
};