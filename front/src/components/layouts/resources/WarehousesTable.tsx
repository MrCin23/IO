import { Warehouse } from "@/types";
import { useTranslation } from "react-i18next";

const WarehousesTable = ({ warehouses }: { warehouses: Warehouse[] }) => {
    const { t } = useTranslation();

    return (
        <div className="overflow-x-auto">
            <table className="min-w-full table-auto border-collapse">
                <caption className="text-xl font-semibold text-center py-4">
                    {t("warehousesList")}
                </caption>
                <thead>
                <tr className="bg-gray-100">
                    <th className="px-4 py-2 text-left border">{t("warehouseId")}</th>
                    <th className="px-4 py-2 text-left border">{t("warehouseName")}</th>
                    <th className="px-4 py-2 text-left border">{t("warehouseLocation")}</th>
                </tr>
                </thead>
                <tbody>
                {warehouses.length > 0 ? (
                    warehouses.map((warehouse) => (
                        <tr key={warehouse.warehouseId} className="border-b">
                            <td className="px-4 py-2">{warehouse.warehouseId}</td>
                            <td className="px-4 py-2">{warehouse.warehouseName}</td>
                            <td className="px-4 py-2">{warehouse.location}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan={3} className="px-4 py-2 text-center">
                            {t("noWarehousesFound")}
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default WarehousesTable;
