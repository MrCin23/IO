import React, {useEffect} from "react";
import "../styles/DonationList.css";

import { FinancialDonation } from "../model/FinancialDonation";
import properties from "../properties/properties.ts";
import axios from "axios";

interface FinancialDonationListProps {
    donations: FinancialDonation[];
    onFetchDonations: () => void;
    isLoading: boolean;
    error: string | null;
}

const FinancialDonationList: React.FC<FinancialDonationListProps> = ({
                                                                         donations = [],
                                                                         onFetchDonations,
                                                                         isLoading,
                                                                         error,
                                                                     }) => {
        useEffect(() => {
        onFetchDonations();
    }, [onFetchDonations]);
    const generateConfirmation = async (donation: FinancialDonation) => {
        try {
            const response = await axios.get(
                `${properties.serverAddress}/api/donations/financial/${donation.id}/confirmation`,
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
            a.download = `confirmation-${donation.id}.pdf`; //nazwa pliku
            a.click();
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Error generating confirmation:", error);
            alert("Nie udało się wygenerować potwierdzenia. Spróbuj ponownie później.");
        }
    };

    return (
        <div className="donation-list-container">
            <h2>Lista darowizn finansowych</h2>
            {isLoading && <p>Ładowanie danych...</p>}
            {error && <p className="error-message">{error}</p>}
            {!isLoading && !error && donations.length === 0 && <p>Brak darowizn finansowych</p>}
            {!isLoading && donations.length > 0 && (
                <div className="donation-list-wrapper">
                    <table className="donation-list">
                        <thead>
                        <tr>
                            <th>Cel</th>
                            <th>Data</th>
                            <th>Kwota</th>
                            <th>Waluta</th>
                            <th>Status</th>
                            <th>Akcje</th>
                        </tr>
                        </thead>
                        <tbody>
                        {donations.map((donation) => (
                            <tr key={donation.id}>
                                <td>{donation.needName}</td>
                                <td>{donation.date.toString()}</td>
                                <td>{donation.amount}</td>
                                <td>{donation.currency}</td>
                                <td>{donation.status}</td>
                                <td>
                                    <button
                                        className="generate-button"
                                        onClick={() => generateConfirmation(donation)}
                                        disabled={donation.status !== "ACCEPTED"}
                                    >
                                        Wygeneruj potwierdzenie
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
        )};

export default FinancialDonationList;
