import { useEffect, useState } from "react";
import "../styles/VolunteerGroupList.css"; // Assuming you create a CSS file for styling
import { useTranslation } from "react-i18next";
import {VolunteerGroup} from "@/components/layouts/volunteer/models/VolunteerGroup.tsx";

function VolunteerGroupList() {
    const [volunteerGroups, setVolunteerGroups] = useState<VolunteerGroup[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const { t } = useTranslation();

    useEffect(() => {
        // Simulate loading state
        // setIsLoading(true);

        // Hardcoded volunteer group data
        setVolunteerGroups([
            {
                id: "1",
                name: "Team A",
                members: [
                    { id: "1", username: "johndoe", firstName: "John", lastName: "Doe", active: true },
                    { id: "2", username: "alexsmith", firstName: "Alex", lastName: "Smith", active: true },
                ],
            },
            {
                id: "2",
                name: "Team B",
                members: [
                    { id: "3", username: "janedoe", firstName: "Jane", lastName: "Doe", active: false },
                ],
            },
        ]);
    }, []);

    return (
        <div className="volunteer-group-list-container">
            <h2>{t("volunteerGroupListTitle")}</h2>
            {isLoading && <p>{t("loading")}</p>}
            {!isLoading && volunteerGroups.length === 0 && (
                <p>{t("noVolunteerGroups")}</p>
            )}
            {!isLoading && volunteerGroups.length > 0 && (
                <div className="volunteer-group-list">
                    {volunteerGroups.map((group) => (
                        <div key={group.id} className="volunteer-group">
                            <h3>{group.name}</h3>
                            <table>
                                <thead>
                                    <tr>
                                        <th>{t("username")}</th>
                                        <th>{t("firstName")}</th>
                                        <th>{t("lastName")}</th>
                                        <th>{t("status")}</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {group.members.map((member) => (
                                        <tr key={member.id}>
                                            <td>{member.username}</td>
                                            <td>{member.firstName}</td>
                                            <td>{member.lastName}</td>
                                            <td>
                                                {member.active
                                                    ? t("active")
                                                    : t("inactive")}
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

export default VolunteerGroupList;
