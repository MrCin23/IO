import {useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import MyAccountPage from "@/pages/uwierzytelnianie/MyAccountPage";
import axios from "@/api/Axios.tsx";
import {UpdateVolunteer} from "@/components/layouts/volunteer/models/Volunteer.tsx";
import {Account} from "@/models/uwierzytelnianie/Account.tsx";
import "../styles/VolunteerInfoPage.css"; // Assuming you create a CSS file for styling


const volunteerInfoPage = () => {
    const { t } = useTranslation();
    const [user, setUser] = useState<Account | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`/auth/me`);
                setUser(response.data);
            } catch (err) {
                console.error('Błąd podczas pobierania danych użytkownika:', err);
            }
        };

        fetchUser();
    }, []);

    const handleEditProfile = () => {
        setIsEditing(true); // Open the editing form
    };

    const handleSaveProfile = async () => {
        // Here, you can call an API or handle the profile update logic
        console.log("Updated First Name:", firstName);
        console.log("Updated Last Name:", lastName);

        const updateVolunteer: UpdateVolunteer = {firstName, lastName};

        await axios.put(`/volunteers/${user?.id}`, updateVolunteer).then(
            (response) => {
                setUser(response.data)
            })
            .catch((error) => console.log(error))

        alert(t("volunteer.profileUpdatedSuccessfully")); // Optional success message
        setFirstName("")
        setLastName("")
        setIsEditing(false); // Close the editing form
    };

    return (
        <div>
            {!isEditing ? (
                <>
                    <button
                        onClick={() => handleEditProfile()}
                        className="edit-group-button"
                    >
                        {t("volunteer.edit")}
                    </button>
                    <MyAccountPage />
                </>
            ) : (
                <div className="edit-profile-form">
                    <h3>{t("volunteer.editProfile")}</h3>
                    <form onSubmit={(e) => e.preventDefault()}>
                        <div>
                            <label>{t("volunteer.firstName")}</label>
                            <input
                                type="text"
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                placeholder={t("volunteer.enterFirstName")}
                            />
                        </div>
                        <div>
                            <label>{t("volunteer.lastName")}</label>
                            <input
                                type="text"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                placeholder={t("volunteer.enterLastName")}
                            />
                        </div>
                        <div>
                            <button
                                type="button"
                                onClick={handleSaveProfile}
                                className="save-profile-button"
                            >
                                {t("volunteer.save")}
                            </button>
                            <button
                                type="button"
                                onClick={() => setIsEditing(false)}
                                className="cancel-edit-button"
                            >
                                {t("volunteer.cancel")}
                            </button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );
};

export default volunteerInfoPage;
