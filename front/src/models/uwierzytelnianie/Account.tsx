import { Role } from "./Role";

export interface Account {
    id: string;
    username: string;
    firstName: string;
    lastName: string;
    active: boolean;
    lastLogin: string | null;
    role: Role;
}