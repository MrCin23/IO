import WarehouseForm from "../components/layouts/resources/WarehouseForm.tsx";
import {useTranslation} from "react-i18next";

export const CreateWarehouse = () => {
    const { t } = useTranslation();
    return (
        <div className="min-h-screen flex items-center justify-center p-4">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl">
                <div className="text-center">
                    <h1 className="text-3xl font-semibold">{(t("resources.createWarehouse"))}</h1>
                    <p className="mt-2 text-gray-600">
                        {t("resources.blablaWarehouse")}
                    </p>
                </div>
                <div className="mt-8">
                    <WarehouseForm />
                </div>
            </div>
        </div>
    );
};

export default CreateWarehouse;
