import {useEffect, useState} from "react";
import "../styles/DonationList.css";

import { FinancialDonation } from "@/components/layouts/donor/model/FinancialDonation.ts";
import api from "@/api/Axios.tsx";
import {useTranslation} from "react-i18next";


function FinancialDonationList()
{

    const {t} = useTranslation();

    const [financialDonations, setFinancialDonations] = useState<FinancialDonation[]>([]);
    const [isLoading, setIsLoading] = useState(false);

    const fetchFinancialDonations = async () => {
        setIsLoading(true);
        try {
            const response = await api.get<FinancialDonation[]>(
                `/donations/financial/account/all`);
            console.log(response);
            setFinancialDonations(response.data);
            if(response.status === 204 || response.status === 200) {
                setIsLoading(false);
            }
        } catch (err) {
            console.error(err);
        }
    };


    useEffect(() => {
        fetchFinancialDonations().then(r => console.log(r))
    }, []);


    const generateConfirmation = async (donation: FinancialDonation) => {
        try {
            const response = await api.get(
                `/donations/financial/${donation.id}/confirmation`,
                {
                    responseType: "blob", // Oczekujemy odpowiedzi w postaci blob (np. PDF)
                }
            );

            // Sprawdzenie odpowiedzi
            if (response.status !== 200) {
                throw new Error(t("Failed to generate confirmation"));
            }
            // Tworzenie URL z blobem i pobranie pliku
            const blob = new Blob([response.data], { type: "application/pdf" });
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = `confirmation-${donation.id}.pdf`; //nazwa pliku
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (error) {
            alert(t("confirmationFailedAlert"));
        }
    };

    return (
        <div className="donation-list-container">
            <h2>{t("financialDonationsList")}</h2>
            {isLoading && <p>{t("loading")}</p>}
            {!isLoading && financialDonations.length === 0 && <p>{t("noFinancialDonations")}</p>}
            {!isLoading && financialDonations.length > 0 && (
                <div className="donation-list-wrapper">
                    <table className="donation-list">
                        <thead>
                        <tr>
                            <th>{t("listGoal")}</th>
                            <th>{t("listDate")}</th>
                            <th>{t("listAmount")}</th>
                            <th>{t("listCurrency")}</th>
                            <th>{t("listStatus")}</th>
                            <th>{t("listConfirmation")}</th>
                        </tr>
                        </thead>
                        <tbody>
                        {financialDonations.map((donation) => (
                            <tr key={donation.id}>
                                <td>{donation.needDescription}</td>
                                <td>{donation.donationDate.toString()}</td>
                                <td>{donation.amount}</td>
                                <td>{donation.currency}</td>
                                <td>{donation.resourceStatus}</td>
                                <td>
                                    <button
                                        className="generate-button"
                                        onClick={() => generateConfirmation(donation)}
                                        disabled={donation.resourceStatus !== "PRZYDZIELONY"}
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

export default FinancialDonationList;
