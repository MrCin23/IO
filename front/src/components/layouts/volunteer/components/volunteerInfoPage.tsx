import { useState } from "react";
import { useTranslation } from "react-i18next";
import MyAccountPage from "@/pages/uwierzytelnianie/MyAccountPage";

const volunteerInfoPage = () => {
    const { t } = useTranslation();

    const [isEditing, setIsEditing] = useState(false);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");

    const handleEditProfile = () => {
        setIsEditing(true); // Open the editing form
    };

    const handleSaveProfile = () => {
        // Here, you can call an API or handle the profile update logic
        console.log("Updated First Name:", firstName);
        console.log("Updated Last Name:", lastName);

        alert(t("profileUpdatedSuccessfully")); // Optional success message
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
                        {t("edit")}
                    </button>
                    <MyAccountPage />
                </>
            ) : (
                <div className="edit-profile-form">
                    <h3>{t("editProfile")}</h3>
                    <form onSubmit={(e) => e.preventDefault()}>
                        <div>
                            <label>{t("firstName")}</label>
                            <input
                                type="text"
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                placeholder={t("enterFirstName")}
                            />
                        </div>
                        <div>
                            <label>{t("lastName")}</label>
                            <input
                                type="text"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                placeholder={t("enterLastName")}
                            />
                        </div>
                        <div>
                            <button
                                type="button"
                                onClick={handleSaveProfile}
                                className="save-profile-button"
                            >
                                {t("save")}
                            </button>
                            <button
                                type="button"
                                onClick={() => setIsEditing(false)}
                                className="cancel-edit-button"
                            >
                                {t("cancel")}
                            </button>
                        </div>
                    </form>
                </div>
            )}
        </div>
    );
};

export default volunteerInfoPage;
