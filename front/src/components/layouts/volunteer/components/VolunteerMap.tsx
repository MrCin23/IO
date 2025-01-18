import {useEffect, useState} from 'react';
import {Account} from "@/models/uwierzytelnianie/Account.tsx";
import MapView from "@/components/mapComponent/MapView.tsx";
import axios from "@/api/Axios.tsx";
import {useTranslation} from "react-i18next";
import "../styles/VolunteerMap.css"; // Assuming you create a CSS file for styling

export const VolunteerPom = () =>
{
    const { t } = useTranslation()
    const [user, setUser] = useState<Account | null>(null);

    const fetchUser = async () => {
        try {
            const response = await axios.get(`/auth/me`);
            console.log(response.data)
            setUser(response.data);
        } catch (err) {
            console.error('Błąd podczas pobierania danych użytkownika:', err);
        }
    };

    useEffect(() => {
        fetchUser();
    }, []);


    return(
        <div className={"scale-container"}>
            <h3> {t("volunteer.setPosition")}</h3>
            <div className="center-container">
                <MapView pointType={"VOLUNTEER"} canAddPoints={true} canShowPoints={true} pointOwner={user!}></MapView>
            </div>
        </div>);
}
