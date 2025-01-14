import {useEffect, useState} from "react";
import "../../../../styles/DonationList.css";

import { FinancialDonation } from "../../../../model/FinancialDonation.ts";
import properties from "../../../../properties/properties.ts";
import axios from "axios";


function FinancialDonationList()
{

    const [financialDonations, setFinancialDonations] = useState<FinancialDonation[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchFinancialDonations = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const response = await axios.get<FinancialDonation[]>(
                `${properties.serverAddress}/api/donations/financial/account/all`);
            setFinancialDonations(response.data);
        } catch (err) {
            setError("Nie udało się pobrać listy darowizn finansowych. Spróbuj ponownie później.");
            console.error(err);
        } finally {
            setIsLoading(false);
        }
    };


    useEffect(() => {
        fetchFinancialDonations()
    }, [fetchFinancialDonations]);




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
            {!isLoading && !error && financialDonations.length === 0 && <p>Brak darowizn finansowych</p>}
            {!isLoading && financialDonations.length > 0 && (
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
                        {financialDonations.map((donation) => (
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
