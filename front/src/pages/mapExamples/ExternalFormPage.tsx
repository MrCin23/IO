import { useState, useEffect } from "react";
import MapView from "../../components/mapComponent/MapView.tsx";
import axios from "axios";
import { useTranslation } from 'react-i18next';

/**
 * Klasa przykładowa pokazująca użycie map z zewnętrznym formularzem. Może to zezwolić na dodanie obiektu MapPoint podczas np. tworzenia użytkownika
 * Po więcej informacji zajrzyj tu:
 * https://github.com/MrCin23/IO/blob/1b64a3034b4e450451923b48bda0d765f4a1d94e/front/src/components/MapView.tsx
 * @constructor
 */

export const ExternalFormPage = () => {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const { t } = useTranslation();


    const [coordinates, setCoordinates] = useState<{ lat: number; lng: number } | null>(null);

    useEffect(() => {
        if (coordinates) {
            console.log('Coordinates changed:', coordinates);
        }
    }, [coordinates]);

    const handleSavePoint = async (e: React.FormEvent) => {
        e.preventDefault();
        if (coordinates && title && description) {
            if (!title || !description || !coordinates) {
                alert(t("maps.fillEveryField"));
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
                alert(t("maps.pointSaved"));
                setCoordinates(null);
                setTitle("");
                setDescription("");
            } catch (error) {
                console.error(t("maps.error.savePoint"), error);
            }
        }
    };

    return (
        <div style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
            <div style={{ width: "45%" }}>
                <h2>{t("maps.addPoint")}</h2>
                <form onSubmit={handleSavePoint}>
                    <div>
                        <label>{t("maps.title")}</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>
                    <div>
                        <label>{t("maps.description")}</label>
                        <textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                            required
                        ></textarea>
                    </div>
                    <button type="submit">{t("maps.savePoint")}</button>
                </form>
            </div>

            <div style={{ width: "50%", height: "500px" }}>
                <MapView
                    pointType="VOLUNTEER"
                    canAddPoints={true}
                    externalForm={true}
                    canShowPoints={false}
                    setCoordinates={setCoordinates}
                />

            </div>


        </div>
    );
};

