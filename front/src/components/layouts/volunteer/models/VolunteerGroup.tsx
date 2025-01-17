import {Volunteer} from "@/components/layouts/volunteer/models/Volunteer.tsx";

export interface VolunteerGroup {
    id: string;
    name: string;
    members: Volunteer[];
}