import { useState } from "react";
import MapView from "../../components/mapComponent/MapView.tsx";
import axios from "axios";

/**
 * Klasa przykładowa pokazująca użycie map z zewnętrznym formularzem. Może to zezwolić na dodanie obiektu MapPoint podczas np. tworzenia użytkownika
 * Po więcej informacji zajrzyj tu:
 * https://github.com/MrCin23/IO/blob/1b64a3034b4e450451923b48bda0d765f4a1d94e/front/src/components/MapView.tsx
 * @constructor
 */

export const ExternalFormPage = () => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");



    const [coordinates, setCoordinates] = useState<{ lat: number; lng: number } | null>(null);



    const handleSavePoint = async (e: React.FormEvent) => {
        e.preventDefault();
        if (coordinates && title && description) {
            if (!title || !description || !coordinates) {
                alert("Wypełnij wszystkie pola!");
                return;
            }
            try {
                console.log(coordinates);
                await axios.post("api/map", {
                    coordinates: coordinates,
                    title: title,
                    description: description,
                    type: "VOLUNTEER",
                    active: true,
                });
                alert("Punkt zapisany!");
                setCoordinates(null);
                setTitle("");
                setDescription("");
            } catch (error) {
                console.error("Błąd podczas zapisywania punktu:", error);
            }
        }
    };

    return (
        <div style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
            <div style={{ width: "45%" }}>
                <h2>Dodaj punkt</h2>
                <form onSubmit={handleSavePoint}>
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

            <div style={{ width: "50%", height: "500px" }}>
                <MapView
                    pointType="VOLUNTEER"
                    canAddPoints={true}
                    externalForm={true}
                    canShowPoints={true}
                    setCoordinates={setCoordinates}
                />

            </div>


        </div>
    );
};

