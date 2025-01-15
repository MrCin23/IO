import { useState } from "react";
import { Resource } from "@/types";
import api from "../../../api/Axios.tsx";
import {useLocation} from "react-router-dom";

export const ResourcesTable = ({ resources }: { resources: Resource[] }) => {
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
            alert("Invalid status selected.");
            return;
        }

        const resourceToUpdate = resources.find((resource) => resource.resourceId === selectedResourceId);

        if (!resourceToUpdate) return;

        try {
            if (selectedQuantity <= 10) {
                alert("Warning: Low resource quantity!");
            }

            await api.put(`/resources/${selectedResourceId}`, {
                resourceId: resourceToUpdate.resourceId,
                resourceName: resourceToUpdate.resourceName,
                resourceType: resourceToUpdate.resourceType,
                resourceQuantity: selectedQuantity,
                resourceStatus: selectedStatus,
                warehouseId: resourceToUpdate.warehouseId,
            });

            alert("Resource updated successfully");
            window.location.reload();
        } catch (error) {
            console.error("Failed to update resource:", error);
            alert("An unexpected error occurred.");
        }
    };


    return (
        <div className="overflow-x-auto">
            <table className="min-w-full table-auto border-collapse border border-gray-300">
                <caption className="text-lg font-semibold text-gray-800 my-4">
                    A list of resources.
                </caption>
                <thead>
                <tr className="bg-gray-100 border-b border-gray-300">
                    <th className="px-4 py-2 text-left font-medium text-gray-700 w-[100px]">Resource ID</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Name</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Type</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Quantity</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Status</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Warehouse ID</th>
                    <th className="px-4 py-2 text-left font-medium text-gray-700">Action</th>
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
                            <td className="px-4 py-2">{resource.resourceStatus}</td>
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
                                        Edit
                                    </button>
                                )}

                                {selectedResourceId === resource.resourceId && !isVictimPath && (
                                    <div className="mt-2">
                                        <div>
                                            <label>
                                                Status:
                                                <select
                                                    className="border border-gray-300 rounded px-2 py-1 ml-2"
                                                    value={selectedStatus}
                                                    onChange={(e) => setSelectedStatus(e.target.value)}
                                                >
                                                    <option value="ACCEPTED">ACCEPTED</option>
                                                    <option value="PENDING">PENDING</option>
                                                    <option value="REJECTED">REJECTED</option>
                                                </select>
                                            </label>
                                        </div>
                                        <div className="mt-2">
                                            <label>
                                                Quantity:
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
                                            Update
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
