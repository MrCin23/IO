import {useEffect, useState} from "react";
import "../../../../styles/DonationList.css";

import { ItemDonation } from "../../../../model/ItemDonation.ts";
import axios from "axios";
import properties from "../../../../properties/properties.ts";


function ItemDonationList() {

    const [itemDonations, setItemDonations] = useState<ItemDonation[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchItemDonations = async () => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.get<ItemDonation[]>(
                `${properties.serverAddress}/api/donations/item/account/all`);
            setItemDonations(response.data);
        } catch (err) {
            setError("Nie udało się pobrać listy darowizn rzeczowych. Spróbuj ponownie później.");
            console.error(err);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchItemDonations();
    }, [fetchItemDonations]);


    const generateConfirmation = async (donation: ItemDonation) => {
        try {
            const response = await axios.get(
                `${properties.serverAddress}/api/donations/item/${donation.id}/confirmation`,
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
            console.error("Error generating confirmation:", error);
            alert("Nie udało się wygenerować potwierdzenia. Spróbuj ponownie później.");
        }
    };

    return (
        <div className="donation-list-container">
            <h2>Lista darowizn rzeczowych</h2>
            {isLoading && <p>Ładowanie danych...</p>}
            {error && <p className="error-message">{error}</p>}
            {!isLoading && !error && itemDonations.length === 0 && <p>Brak darowizn finansowych</p>}
            {!isLoading && itemDonations.length > 0 && (
                <table className="donation-list">
                    <thead>
                    <tr>
                        <th>Cel</th>
                        <th>Data</th>
                        <th>Przedmiot</th>
                        <th>Opis</th>
                        <th>Ilość</th>
                        <th>Kategoria</th>
                        <th>Status</th>
                        <th>Akcje</th>
                    </tr>
                    </thead>
                    <tbody>
                    {itemDonations.map((donation) => (
                        <tr key={donation.id}>
                            <td>{donation.needName}</td>
                            <td>{donation.date.toString()}</td>
                            <td>{donation.itemName}</td>
                            <td>{donation.description}</td>
                            <td>{donation.quantity}</td>
                            <td>{donation.category}</td>
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
            )}
        </div>
    );
};

export default ItemDonationList;
