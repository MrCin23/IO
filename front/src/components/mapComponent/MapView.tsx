import { useState, useEffect } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import axios from "axios";
import {useTranslation} from "react-i18next";

// Ikona markera
const defaultIcon = L.icon({
    iconUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
});

const greyIcon = new L.Icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-grey.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
});

interface Point {
    pointID: number | undefined;
    coordinates: { lat: number; lng: number };
    title: string;
    description: string;
    active: boolean;
}

interface MapViewProps {
    pointType: string;
    canAddPoints?: boolean;
    canShowPoints?: boolean;
    externalForm?: boolean;
    setCoordinates?: (coordinates: { lat: number; lng: number }) => void;
}

/**
 * Komponent odpowiedzialny za widzialną warstwę map. Umożliwia zapis i odczyt punktu/ów.
 * Dla większej uniwersalności, komponent został wyposażony w kilka parametrów.
 * @param pointType wymagany - jest to łańcuch znaków odpowiadający typowi Punktu. Dostępne typy to:
 *     VOLUNTEER,
 *     VICTIM,
 *     AID_ORGANIZATION,
 *     AUTHORITY_REPRESENTATIVE
 * @param canAddPoints opcjonalny (default: false) - definiuje, czy możliwe jest dodawanie punktów na mapie (dodany zostanie punkt typu @param canAddpoints)
 * @param canShowPoints opcjonalny (default: false) - definiuje, czy możliwe jest odczytywanie punktów z mapy (podane zostaną punkty typu @param canAddpoints)
 * @param externalForm opcjonalny (default: false) - jeśli jest ustawiony na true, można korzystać z map z użyciem zewnętrznego formularza. W przeciwnym wypadku używany będzie formularz wyświetlający się w <MapView>
 * @param setCoordinates opcjonalny - jeśli externalForm ustawione jest na true, zezwala na korzystanie z koordynatów poza <MapView>. Patrz: https://github.com/MrCin23/IO/blob/1b64a3034b4e450451923b48bda0d765f4a1d94e/front/src/pages/ExternalFormPage.tsx
 * @constructor
 */
const MapView: React.FC<MapViewProps> = ({ pointType, canAddPoints = false, canShowPoints = false, externalForm = false, setCoordinates }) => {
    const [points, setPoints] = useState<Point[]>([]);
    const { t } = useTranslation();
    const [newPoint, setNewPoint] = useState<Point | null>(null);
    const [formData, setFormData] = useState({
        title: "",
        description: "",
    });

    const MapClickHandler = () => {
        useMapEvents({
            click: (e: { latlng: { lat: number; lng: number; }; }) => {
                if (canAddPoints) {
                    setNewPoint({
                        pointID: undefined,
                        coordinates: { lat: e.latlng.lat, lng: e.latlng.lng },
                        title: "",
                        description: "",
                        active: true
                    });

                    if (externalForm && setCoordinates) {
                        setCoordinates({ lat: e.latlng.lat, lng: e.latlng.lng });
                    }
                }
            },
        });
        return null;
    };

    const fetchPoints = async () => {
        try {
            const response = await axios.get(`api/map/type/${pointType}`);
            setPoints(response.data);
        } catch (error) {
            console.error(t(""), error);
        }
    };

    const handleDelete = async (id: number | undefined) => {
        try {
            console.log(id);
            await axios.delete(`api/map/${id}`);
            setPoints((prev) =>
                prev.filter((point) => point.pointID !== id)
            );
            alert(t("pointDeleted"));
        } catch (error) {
            console.error(t("error.readPoint"), error)
        }
    }

    const handleArchive = async (id: number | undefined, status: boolean) => {
        try {
            await axios.put(`api/map/status/${id}/${status}`);
            await fetchPoints();
            alert(t("pointStateChanged"));
        } catch (error) {
            console.error(t("error.readPoint"), error);
        }
    };

    const savePoint = async () => {
        if (!formData.title || !formData.description || !newPoint) {
            alert(t("fillEveryField"));
            return;
        }
        try {
            await axios.post("api/map", {
                coordinates: newPoint.coordinates,
                title: formData.title,
                description: formData.description,
                type: pointType,
                active: true,
            });
            alert(t("pointSaved"));
            setNewPoint(null);
            setFormData({ title: "", description: "" });
            fetchPoints();
        } catch (error) {
            console.error(t("error.savePoint"), error);
        }
    };

    useEffect(() => {
        fetchPoints();
    }, []);

    return (
        <div style={{ height: "500px", width: "500px", display: "flex", flexDirection: "column" }}>
            <MapContainer
                style={{ height: "500px", width: "500px" }}
                center={[52.2297, 21.0122]}
                zoom={13}
            >
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                />
                {canShowPoints && points.map((point, index) => (
                    <Marker
                        key={index}
                        position={[point.coordinates.lat, point.coordinates.lng]}
                        icon={point.active ? defaultIcon : greyIcon}
                    >
                        <Popup>
                            <strong>{point.title}</strong>
                            <p>{point.description}</p>
                            {/*<button onClick={}>{t("editPoint")}</button>*/}
                            <button onClick={() => handleDelete(point.pointID)}>{t("deletePoint")}</button>
                            <button onClick={() => handleArchive(point.pointID, !point.active)}>{t("switchStatus")}</button>
                        </Popup>
                    </Marker>
                ))}
                {newPoint && canAddPoints && (
                    <Marker
                        position={[newPoint.coordinates.lat, newPoint.coordinates.lng]}
                        icon={defaultIcon}
                    >
                        <Popup>
                            <strong>{t("addPoint")}</strong>
                        </Popup>
                    </Marker>
                )}
                <MapClickHandler />
            </MapContainer>
            {newPoint && canAddPoints && !externalForm && (
                <div style={{ padding: "10px", background: "#282828", marginTop: "10px" }}>
                    <h3>{t("addPoint")}</h3>
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            savePoint();
                        }}
                    >
                        <div>
                            <label>{t("title")}</label>
                            <input
                                type="text"
                                value={formData.title}
                                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                required
                            />
                        </div>
                        <div>
                            <label>{t("description")}</label>
                            <textarea
                                value={formData.description}
                                onChange={(e) =>
                                    setFormData({ ...formData, description: e.target.value })
                                }
                                required
                            ></textarea>
                        </div>
                        <button type="submit">{t("savePoint")}</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default MapView;
