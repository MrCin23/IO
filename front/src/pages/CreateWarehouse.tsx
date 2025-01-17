import { useState } from "react";
import { useTranslation } from "react-i18next";
import MapView from "../components/mapComponent/MapView.tsx";
import WarehouseForm from "../components/layouts/resources/WarehouseForm.tsx";

export const CreateWarehouse = () => {
    const { t } = useTranslation();
    const [coordinates, setCoordinates] = useState<{ lat: number; lng: number } | undefined>(undefined);

    return (
        <div className="min-h-screen flex items-center justify-center p-4">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl">
                <div className="text-center">
                    <h1 className="text-3xl font-semibold">{t("resources.createWarehouse")}</h1>
                    <p className="mt-2 text-gray-600">{t("resources.blablaWarehouse")}</p>
                </div>
                <div className="mt-8 flex justify-center">
                    <div className="w-full max-w-md -ml-12" >
                        <MapView
                            pointType="WAREHOUSE"
                            canAddPoints={true}
                            externalForm={true}
                            setCoordinates={setCoordinates}
                        />
                    </div>
                </div>
                <div className="mt-8">
                    <WarehouseForm coordinates={coordinates}/>
                </div>
            </div>
        </div>
    );
};

export default CreateWarehouse;
