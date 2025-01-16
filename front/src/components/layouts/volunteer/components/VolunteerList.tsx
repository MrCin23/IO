import { useEffect, useState } from "react";
import "../styles/VolunteerList.css"; // Assuming you create a CSS file for styling
import { useTranslation } from "react-i18next";
import {Volunteer} from "@/components/layouts/volunteer/models/Volunteer.tsx";
import axios from "@/api/Axios.tsx";

function VolunteerList() {
    const { t } = useTranslation();

    const [volunteers, setVolunteers] = useState<Volunteer[]>([]);
    const [filter, setFilter] = useState('');

    useEffect(() => {
        axios.get('/volunteers').then((response) => setVolunteers(response.data));
    }, []);

    return (
        <div className="container my-5">
            <h2>{t("volunteerListTitle")}</h2>
            <input
                type="text"
                className="form-control mb-3"
                placeholder={t("filterByName")}
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
            />

            <div className="table-responsive">
                <table className="table table-striped table-hover table-bordered">
                    <thead className='table-dark text-center'>
                    <tr>
                        <th>{t("")}ID</th>
                        <th>{t("username")}Nazwa </th>
                        <th>{t("firstName")}</th>
                        <th>{t("lastName")}</th>
                        <th>{t("lastLogon")}</th>
                        <th>{t("role")}</th>
                        <th>{t("status")}</th>
                    </tr>
                    </thead>
                    <tbody>
                    {volunteers.filter((user) =>
                        user.username.toLowerCase().includes(filter.toLowerCase())
                    )
                    .map((volunteer) => (
                        <tr key={volunteer.id}>
                            <td>{volunteer.id}</td>
                            <td>{volunteer.username}</td>
                            <td>{volunteer.firstName}</td>
                            <td>{volunteer.lastName}</td>
                            <td>{volunteer.lastLogin}</td>
                            <td>{volunteer.role.roleName}</td>
                            <td>{volunteer.active ? 'Aktywny' : 'Nieaktywny'}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default VolunteerList;
