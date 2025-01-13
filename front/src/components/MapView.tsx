import { useState, useEffect } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMapEvents } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import axios from "axios";

// Ikona markera
const defaultIcon = L.icon({
    iconUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
});

interface Point {
    coordinates: { lat: number; lng: number };
    title: string;
    description: string;
}

const MapView = () => {
    const [points, setPoints] = useState<Point[]>([]); // Punkty mapy
    const [newPoint, setNewPoint] = useState<Point | null>(null); // Nowy punkt
    const [formData, setFormData] = useState({
        title: "",
        description: "",
    }); // Dane formularza

    // Obsługa kliknięcia na mapę
    const MapClickHandler = () => {
        useMapEvents({
            click: (e) => {
                setNewPoint({
                    coordinates: { lat: e.latlng.lat, lng: e.latlng.lng },
                    title: "",
                    description: "",
                });
            },
        });
        return null;
    };

    // Pobieranie danych punktów z backendu
    const fetchPoints = async () => {
        try {
            const response = await axios.get('api/map'); // Adres backendu
            setPoints(response.data);
        } catch (error) {
            console.error("Błąd podczas pobierania punktów mapy:", error);
        }
    };

    // Obsługa wysyłania punktu
    const savePoint = async () => {
        if (!formData.title || !formData.description || !newPoint) {
            alert("Wypełnij wszystkie pola!");
            return;
        }
        try {
            await axios.post("api/map", {
                coordinates: newPoint.coordinates,
                title: formData.title,
                description: formData.description,
                type: "VOLUNTEER", // Typ można zmieniać według potrzeb
                active: true,
            });
            alert("Punkt zapisany!");
            setNewPoint(null);
            setFormData({ title: "", description: "" });
            fetchPoints();
        } catch (error) {
            console.error("Błąd podczas zapisywania punktu:", error);
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
                {points.map((point, index) => (
                    <Marker
                        key={index}
                        position={[point.coordinates.lat, point.coordinates.lng]}
                        icon={defaultIcon}
                    >
                        <Popup>
                            <strong>{point.title}</strong>
                            <p>{point.description}</p>
                        </Popup>
                    </Marker>
                ))}
                {newPoint && (
                    <Marker
                        position={[newPoint.coordinates.lat, newPoint.coordinates.lng]}
                        icon={defaultIcon}
                    >
                        <Popup>
                            <strong>Nowy punkt</strong>
                            <p>Dodaj szczegóły poniżej</p>
                        </Popup>
                    </Marker>
                )}
                <MapClickHandler />
            </MapContainer>

            {newPoint && (
                <div style={{ padding: "10px", background: "#282828", marginTop: "10px" }}>
                    <h3>Dodaj nowy punkt</h3>
                    <form
                        onSubmit={(e) => {
                            e.preventDefault();
                            savePoint();
                        }}
                    >
                        <div>
                            <label>Tytuł:</label>
                            <input
                                type="text"
                                value={formData.title}
                                onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                                required
                            />
                        </div>
                        <div>
                            <label>Opis:</label>
                            <textarea
                                value={formData.description}
                                onChange={(e) =>
                                    setFormData({ ...formData, description: e.target.value })
                                }
                                required
                            ></textarea>
                        </div>
                        <button type="submit">Zapisz</button>
                    </form>
                </div>
            )}
        </div>
    );
};

export default MapView;
