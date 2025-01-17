import { useState, useEffect } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
// import axios from "axios";
import api from "../../api/Axios.tsx"
import {useTranslation} from "react-i18next";
import "./maps.css"

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
    type: string;
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
    const [editPoint, setEditPoint] = useState<Point | null>(null);
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
                        type: pointType,
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
            const response = await api.get(`map/type/${pointType}`);
            setPoints(response.data);
        } catch (error) {
            console.error(t("maps.error.noDescription"), error);
        }
    };

    const handleDelete = async (id: number | undefined) => {
        try {
            console.log(id);
            await api.delete(`map/${id}`);
            setPoints((prev) =>
                prev.filter((point) => point.pointID !== id)
            );
            alert(t("maps.pointDeleted"));
        } catch (error) {
            console.error(t("maps.error.readPoint"), error)
        }
    }

    const handleArchive = async (id: number | undefined, status: boolean) => {
        try {
            await api.put(`map/status/${id}/${status}`);
            await fetchPoints();
            alert(t("maps.pointStateChanged"));
        } catch (error) {
            console.error(t("maps.error.readPoint"), error);
        }
    };

    // const handleEdit = async (id: number | undefined) => {
    //
    //
    //     try {
    //         await api.put(`map/status/${id}/${status}`);
    //         await fetchPoints();
    //         alert(t("maps.pointStateChanged"));
    //     } catch (error) {
    //         console.error(t("maps.error.readPoint"), error);
    //     }
    // };

    const handleEdit = (point: Point) => {
        setEditPoint(point);
        setFormData({ title: point.title, description: point.description });
    };

    const saveEdit = async (point: Point) => {
        try {
            await api.put(`map/${point.pointID}`, {
                title: formData.title,
                description: formData.description,
            });
            if(point.type == "WAREHOUSE") {
                const response = await api.get(`warehouses/point/${point.pointID}`, {})
                response.data.warehouseName = formData.title;
                await api.put(`warehouses/${response.data.warehouseId}`, response.data);
            }
            alert(t("maps.pointUpdated"));
            setEditPoint(null);
            fetchPoints();
        } catch (error) {
            console.error(t("maps.error.savePoint"), error);
        }
    };

    const cancelEdit = () => {
        setEditPoint(null);
    };

    const savePoint = async () => {
        if (!formData.title || !formData.description || !newPoint) {
            alert(t("maps.fillEveryField"));
            return;
        }
        try {
            await api.post("map", {
                coordinates: newPoint.coordinates,
                title: formData.title,
                description: formData.description,
                type: pointType,
                active: true,
            });
            alert(t("maps.pointSaved"));
            setNewPoint(null);
            setFormData({ title: "", description: "" });
            fetchPoints();
        } catch (error) {
            console.error(t("maps.error.savePoint"), error);
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
                        {/*<Popup>*/}
                        {/*    <div>*/}
                        {/*        <strong>{point.title}</strong>*/}
                        {/*        <p>{point.description}</p>*/}
                        {/*    </div>*/}
                        {/*    <button onClick={() => handleDelete(point.pointID)}>{t("maps.deletePoint")}</button>*/}
                        {/*    <button*/}
                        {/*        onClick={() => handleArchive(point.pointID, !point.active)}>{t("maps.switchStatus")}</button>*/}
                        {/*    <button onClick={() => handleEdit(point.pointID)}>{t("maps.editPoint")}</button>*/}
                        {/*</Popup>*/}
                        <Popup>
                            {editPoint?.pointID === point.pointID ? (
                                <div className="popup-content">
                                    <div>
                                        <label>{t("maps.title")}</label>
                                        <input
                                            type="text"
                                            value={formData.title}
                                            onChange={(e) =>
                                                setFormData({
                                                    ...formData,
                                                    title: e.target.value,
                                                })
                                            }
                                        />
                                    </div>
                                    <div>
                                        <label>{t("maps.description")}</label>
                                        <textarea
                                            value={formData.description}
                                            onChange={(e) =>
                                                setFormData({
                                                    ...formData,
                                                    description: e.target.value,
                                                })
                                            }
                                        ></textarea>
                                    </div>
                                    <div className="popup-buttons">
                                        <button onClick={() => saveEdit(editPoint!)}>{t("maps.savePoint")}</button>
                                        <button onClick={cancelEdit}>{t("maps.cancelEdit")}</button>
                                    </div>
                                </div>
                            ) : (
                                <div className="popup-content">
                                    <div>
                                        <strong>{point.title}</strong>
                                        <p>{point.description}</p>
                                    </div>
                                    <div className="popup-buttons">
                                        <button onClick={() => handleDelete(point.pointID)}>{t("maps.deletePoint")}</button>
                                        <button onClick={() => handleArchive(point.pointID, !point.active)}>
                                            {t("maps.switchStatus")}
                                        </button>
                                        <button onClick={() => handleEdit(point)}>{t("maps.editPoint")}</button>
                                    </div>
                                </div>
                            )}
                        </Popup>
                    </Marker>
                ))}
                {newPoint && canAddPoints && (
                    <Marker
                        position={[newPoint.coordinates.lat, newPoint.coordinates.lng]}
                        icon={defaultIcon}
                    >
                        <Popup>
                            <strong>{t("maps.addPoint")}</strong>
                        </Popup>
                    </Marker>
                )}
                <MapClickHandler />
            </MapContainer>
            {newPoint && canAddPoints && !externalForm && (
                <div style={{ padding: "10px", background: "#282828", marginTop: "10px" }}>
                    <h3>{t("maps.addPoint")}</h3>
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            savePoint();
                        }}
                    >
                        <div>
                            <label>{t("maps.title")}</label>
                            <input
                                type="text"
                                value={formData.title}
                                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                required
                            />
                        </div>
                        <div>
                            <label>{t("maps.description")}</label>
                            <textarea
                                value={formData.description}
                                onChange={(e) =>
                                    setFormData({ ...formData, description: e.target.value })
                                }
                                required
                            ></textarea>
                        </div>
                        <button type="submit">{t("maps.savePoint")}</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default MapView;
