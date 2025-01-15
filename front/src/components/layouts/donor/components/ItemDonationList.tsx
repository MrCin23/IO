import {useEffect, useState} from "react";
import "../styles/DonationList.css";

import { ItemDonation } from "@/components/layouts/donor/model/ItemDonation.ts";
import api from "@/api/Axios.tsx";
import {useTranslation} from "react-i18next";


function ItemDonationList() {

    const [itemDonations, setItemDonations] = useState<ItemDonation[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const { t } = useTranslation();

    const fetchItemDonations = async () => {
        setIsLoading(true);
        try {
            const response = await api.get<ItemDonation[]>(
                `/donations/item/account/all`);
            setItemDonations(response.data);
            console.log(response.data);
            if(response.status === 204 || response.status === 200) {
                setIsLoading(false);
            }
        } catch (err) {
            console.error(err);
        }
    };

    useEffect(() => {
        fetchItemDonations();
    }, []);


    const generateConfirmation = async (donation: ItemDonation) => {
        try {
            const response = await api.get(
                `/donations/item/${donation.id}/confirmation`,
                {
                    responseType: "blob", // Oczekujemy odpowiedzi w postaci blob (np. PDF)
                }
            );

            // Sprawdzenie odpowiedzi
            if (response.status !== 200) {
                throw new Error("Failed to generate confirmation");
            }

            // Tworzenie URL z blobem i pobranie pliku
            const blob = new Blob([response.data], { type: "application/pdf" });
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = `confirmation-${donation.id}.pdf`;
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (error) {
            alert(t("confirmationFailedAlert"));
        }
    };

    return (
        <div className="donation-list-container">
            <h2>{t("itemDonationsList")}</h2>
            {isLoading && <p>{t("loading")}</p>}
            {!isLoading && itemDonations.length === 0 && <p>{t("noItemDonations")}</p>}
            {!isLoading && itemDonations.length > 0 && (
                <div className="donation-list-wrapper">
                    <table className="donation-list">
                        <thead>
                        <tr>
                            <th>{t("listGoal")}</th>
                            <th>{t("listDate")}</th>
                            <th>{t("listItem")}</th>
                            <th>{t("listDescription")}</th>
                            <th>{t("listQuantity")}</th>
                            <th>{t("listCategory")}</th>
                            <th>{t("listStatus")}</th>
                            <th>{t("listConfirmation")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {itemDonations.map((donation) => (
                            <tr key={donation.id}>
                                <td>{donation.needDescription}</td>
                                <td>{donation.donationDate.toString()}</td>
                                <td>{donation.itemName}</td>
                                <td>{donation.description}</td>
                                <td>{donation.resourceQuantity}</td>
                                <td>{donation.category}</td>
                                <td>{donation.resourceStatus}</td>
                                <td>
                                    <button
                                        className="generate-button"
                                        onClick={() => generateConfirmation(donation)}
                                        disabled={donation.resourceStatus !== "ACCEPTED"}
                                    >
                                        {t("generateConfirmation")}
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}

export default ItemDonationList;
