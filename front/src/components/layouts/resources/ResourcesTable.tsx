import { useState } from "react";
import { Resource } from "@/types";
import api from "../../../api/Axios.tsx";
import { useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";

export const ResourcesTable = ({ resources }: { resources: Resource[] }) => {
    const { t } = useTranslation();

    const [selectedResourceId, setSelectedResourceId] = useState<number | null>(null);
    const [selectedStatus, setSelectedStatus] = useState<string>("");
    const [selectedQuantity, setSelectedQuantity] = useState<number | null>(null);

    const location = useLocation();
    const isVictimPath = location.pathname.includes('/victim/resources');

    const handleEditClick = (resourceId: number, status: string, quantity: number) => {
        setSelectedResourceId(resourceId);
        setSelectedStatus(status);
        setSelectedQuantity(quantity);
    };

    const handleUpdate = async () => {
        if (!selectedResourceId || !selectedStatus || selectedQuantity === null) return;

        const validStatuses = ["ACCEPTED", "PENDING", "REJECTED"];
        if (!validStatuses.includes(selectedStatus)) {
            alert(t("invalidStatusSelected"));
            return;
        }

        const statusMap: { [key in string]: string } = {
            "Zaakceptowany": "ACCEPTED",
            "Oczekujący": "PENDING",
            "Odrzucony": "REJECTED",
            "ACCEPTED": "ACCEPTED",
            "PENDING": "PENDING",
            "REJECTED": "REJECTED"
        };

        const resourceToUpdate = resources.find((resource) => resource.resourceId === selectedResourceId);

        if (!resourceToUpdate) return;

        try {
            if (selectedQuantity <= 10) {
                alert(t("lowResourceWarning"));
            }

            await api.put(`/resources/${selectedResourceId}`, {
                resourceId: resourceToUpdate.resourceId,
                resourceName: resourceToUpdate.resourceName,
                resourceType: resourceToUpdate.resourceType,
                resourceQuantity: selectedQuantity,
                resourceStatus: statusMap[selectedStatus as "Zaakceptowany" | "Oczekujący" | "Odrzucony"],
                warehouseId: resourceToUpdate.warehouseId,
            });

            alert(t("resourceUpdatedSuccess"));
            window.location.reload();
        } catch (error) {
            console.error(t("resourceUpdateFailed"), error);
            alert(t("unexpectedErrorOccurred"));
        }
    };

    const handleDelete = async (resourceId: number) => {
        if (!window.confirm(t("confirmDeleteResource"))) return;

        try {
            await api.delete(`/resources/${resourceId}`);
            alert(t("resourceDeletedSuccess"));
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert(t("unexpectedErrorOccurred"));
        }
    };

    return (
        <div className="overflow-x-auto">
            <table className="min-w-full table-auto border-collapse border border-gray-300">
                <caption className="text-lg font-semibold text-gray-800 my-4">
                    {t("resourcesList")}
                </caption>
                <thead>
                <tr className="bg-gray-100 border-b border-gray-300">
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("warehouseId")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("resourceName")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("resourceType")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("quantity")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("status")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("warehouse")}</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">{t("action")}</th>
                </tr>
                </thead>
                <tbody>
                {resources
                    .slice()
                    .sort((a, b) => a.resourceId - b.resourceId)
                    .map((resource) => (
                        <tr
                            key={resource.resourceId}
                            className={`border-b border-gray-300 ${
                                resource.resourceQuantity <= 10 ? "bg-red-100" : ""
                            }`}
                        >
                            <td className="px-4 py-2">{resource.resourceId}</td>
                            <td className="px-4 py-2">{resource.resourceName}</td>
                            <td className="px-4 py-2">{resource.resourceType}</td>
                            <td className="px-4 py-2">{resource.resourceQuantity}</td>
                            <td className="px-4 py-2">
                                {t(`status${resource.resourceStatus.charAt(0).toUpperCase() + resource.resourceStatus.slice(1).toLowerCase()}`)}
                            </td>
                            <td className="px-4 py-2">{resource.warehouseId ?? ""}</td>
                            <td className="px-4 py-2">
                                {!isVictimPath && (
                                    <button
                                        className="ml-2 bg-blue-500 text-white px-3 py-1 rounded"
                                        onClick={() =>
                                            handleEditClick(
                                                resource.resourceId,
                                                resource.resourceStatus,
                                                resource.resourceQuantity
                                            )
                                        }
                                    >
                                        {t("edit")}
                                    </button>
                                )}

                                {selectedResourceId === resource.resourceId && !isVictimPath && (
                                    <div className="mt-2">
                                        <div>
                                            <label>
                                                {t("status")}:
                                                <select
                                                    className="border border-gray-300 rounded px-2 py-1 ml-2"
                                                    value={selectedStatus}
                                                    onChange={(e) => setSelectedStatus(e.target.value)}
                                                >
                                                    <option value="ACCEPTED">{t("statusAccepted")}</option>
                                                    <option value="PENDING">{t("statusPending")}</option>
                                                    <option value="REJECTED">{t("statusRejected")}</option>
                                                </select>
                                            </label>
                                        </div>
                                        <div className="mt-2">
                                            <label>
                                                {t("quantity")}:
                                                <input
                                                    type="number"
                                                    className="border border-gray-300 rounded px-2 py-1 ml-2"
                                                    value={selectedQuantity ?? ""}
                                                    onChange={(e) => setSelectedQuantity(parseInt(e.target.value, 10))}
                                                />
                                            </label>
                                        </div>
                                        <button
                                            className="ml-2 mt-2 bg-blue-500 text-white px-3 py-1 rounded"
                                            onClick={handleUpdate}
                                            disabled={!selectedStatus || selectedQuantity === null}
                                        >
                                            {t("update")}
                                        </button>
                                        <button
                                            className="ml-2 mt-2 bg-red-500 text-white px-3 py-1 rounded"
                                            onClick={() => handleDelete(resource.resourceId)}
                                        >
                                            {t("delete")}
                                        </button>
                                    </div>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};
