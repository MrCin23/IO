import { useState } from "react";
import MapView from "../components/MapView"; // Zakładając, że komponent MapView jest w tym samym folderze

export const ExternalFormPage = () => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [isFormVisible, setIsFormVisible] = useState(false);

    // Stan przechowujący punkt, który chcemy dodać
    const [selectedPoint, setSelectedPoint] = useState<{ lat: number; lng: number } | null>(null);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        setIsFormVisible(true);
    };

    // Funkcja do zapisania punktu po kliknięciu przycisku
    const handleSavePoint = () => {
        if (selectedPoint && title && description) {
            // Wyślij dane do API lub zapis do bazy danych
            console.log("Zapisz punkt:", {
                coordinates: selectedPoint,
                title,
                description,
            });
            alert("Punkt zapisany!");
        }
    };

    return (
        <div style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
            {/* Formularz do wprowadzenia tytułu i opisu */}
            <div style={{ width: "45%" }}>
                <h2>Dodaj punkt</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Tytuł:</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>Opis:</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                        ></textarea>
                    </div>
                    <button type="submit">Zapisz punkt</button>
                </form>
            </div>

            {/* Mapa obok formularza */}
            <div style={{ width: "50%", height: "500px" }}>
                <MapView
                    pointType="volunteer"
                    canAddPoints={true}  // Możliwość dodawania punktów
                    isExternalForm={true}  // Zewnętrzny formularz
                    externalTitle={title}
                    externalDescription={description}
                    canShowPoints={true}  // Pokazywanie punktów na mapie// Funkcja do zapisywania punktu
                    onPointSelected={setSelectedPoint}  // Funkcja do ustawiania punktu na mapie
                />

            </div>

            {isFormVisible && selectedPoint && (
                <div>
                    <button onClick={handleSavePoint}>Zapisz punkt na mapie</button>
                </div>
            )}
        </div>
    );
};

