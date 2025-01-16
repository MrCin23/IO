import {Role} from "@/models/uwierzytelnianie/Role.tsx";

export interface Volunteer {
    id: string;
    username: string;
    firstName: string;
    lastName: string;
    active: boolean;
    lastLogin: string | null;
    role: Role;
}
