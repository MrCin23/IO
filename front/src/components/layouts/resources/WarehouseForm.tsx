import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate, useLocation } from "react-router-dom";
import api from "../../../api/Axios.tsx";
import {useTranslation} from "react-i18next";
import axios from "axios";

export interface Warehouse {
    warehouseId?: number;
    warehouseName: string;
    location: string;
}

interface WarehouseFormProps {
    coordinates?: { lat: number; lng: number };
}

const WarehouseForm: React.FC<WarehouseFormProps> = ({ coordinates }) => {
    const { t } = useTranslation();
    const form = useForm<Warehouse>({
        defaultValues: {
            warehouseName: "",
            location: "",
        },
    });
    const type = "WAREHOUSE";

    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        if (coordinates) {
            const formattedLocation = `${coordinates.lat}, ${coordinates.lng}`;
            form.setValue("location", formattedLocation);
        }
    }, [coordinates, form]);

    const onSubmit = async (values: Warehouse) => {
        try {
            const response = await api.post("/warehouses", values);
            await api.post("/map", {
                coordinates: coordinates,
                title: values.warehouseName,
                description: `${values.warehouseName} type: ${type}`,
                type: type,
                active: true,
            });

            if (response.status === 201) {
                alert(t("resources.warehouseSuccess"));
                form.reset();

                if (location.pathname.includes('/organization/warehouses/create')) {
                    navigate('/organization/warehouses');
                } else if (location.pathname.includes('/authority/warehouses/create')) {
                    navigate('/authority/warehouses');
                } else {
                    navigate('/');
                }
            } else {
                console.error("Failed to add warehouse:", response.data);
                alert(t("resources.warehouseFailed"));
            }
        } catch (error) {
            console.error("An unexpected error occurred:", error);
            alert(t("resources.warehouseError"));
        }
    };

    return (
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6 max-w-lg mx-auto">
            <div className="flex flex-col">
                <label htmlFor="warehouseName" className="text-lg font-semibold text-gray-700">
                    {t("resources.warehouseName")}
                </label>
                <input
                    id="warehouseName"
                    {...form.register("warehouseName", { required: t("resources.warehouseNameRequired") })}
                    className="mt-2 p-3 border border-gray-300 rounded-md"
                    placeholder={t("resources.enterWarehouseName")}
                />
                {form.formState.errors.warehouseName && (
                    <span className="text-red-600 text-sm mt-1">
                        {form.formState.errors.warehouseName.message}
                    </span>
                )}
            </div>

            <div className="flex flex-col">
                <label htmlFor="location" className="text-lg font-semibold text-gray-700">
                    {t("resources.location")}
                </label>
                <input
                    id="location"
                    {...form.register("location", { required: t("resources.locationRequired") })}
                    className="mt-2 p-3 border border-gray-300 rounded-md"
                    placeholder={t("resources.enterLocation")}
                    readOnly
                />
                {form.formState.errors.location && (
                    <span className="text-red-600 text-sm mt-1">
                        {form.formState.errors.location.message}
                    </span>
                )}
            </div>

            <div className="flex justify-center">
                <button
                    type="submit"
                    className="bg-blue-600 text-white px-6 py-3 rounded-md hover:bg-blue-700 focus:outline-none"
                >
                    {t("resources.addWarehouse")}
                </button>
            </div>
        </form>
    );
};

export default WarehouseForm;
