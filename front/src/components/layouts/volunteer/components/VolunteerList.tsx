import { useEffect, useState } from "react";
import "../styles/VolunteerList.css"; // Assuming you create a CSS file for styling
import { useTranslation } from "react-i18next";
import {Volunteer} from "@/components/layouts/volunteer/models/Volunteer.tsx";

function VolunteerList() {
    const [volunteers, setVolunteers] = useState<Volunteer[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const { t } = useTranslation();

    useEffect(() => {
        // Simulate loading state
        setIsLoading(true);

        // Hardcoded volunteer data

        setVolunteers([
            { id: "1", username: "johndoe", firstName: "John", lastName: "Doe", active: true },
            { id: "2", username: "janedoe", firstName: "Jane", lastName: "Doe", active: false },
            { id: "3", username: "alexsmith", firstName: "Alex", lastName: "Smith", active: true },
        ]);
        setIsLoading(false);
    }, []);

    return (
        <div className="volunteer-list-container">
            <h2>{t("volunteerListTitle")}</h2>
            {isLoading && <p>{t("loading")}</p>}
            {!isLoading && volunteers.length === 0 && <p>{t("noVolunteers")}</p>}
            {!isLoading && volunteers.length > 0 && (
                <table className="volunteer-list">
                    <thead>
                    <tr>
                        <th>{t("username")}</th>
                        <th>{t("firstName")}</th>
                        <th>{t("lastName")}</th>
                        <th>{t("status")}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {volunteers.map((volunteer) => (
                        <tr key={volunteer.id}>
                            <td>{volunteer.username}</td>
                            <td>{volunteer.firstName}</td>
                            <td>{volunteer.lastName}</td>
                            <td>
                                {volunteer.active ? t("active") : t("inactive")}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default VolunteerList;
