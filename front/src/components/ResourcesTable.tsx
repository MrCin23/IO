import { useState } from "react";
import { Resource } from "@/types";

const ResourcesTable = ({ resources }: { resources: Resource[] }) => {
    const [selectedResourceId, setSelectedResourceId] = useState<number | null>(null);
    const [selectedStatus, setSelectedStatus] = useState<string>("");

    const handleStatusChange = (resourceId: number, status: string) => {
        setSelectedResourceId(resourceId);
        setSelectedStatus(status);
    };

    const handleStatusUpdate = async () => {
        if (!selectedResourceId || !selectedStatus) return;

        const resourceStatus = selectedStatus === "PRZYDZIELONY" ? "PRZYDZIELONY" : "NIEPRZYDZIELONY";

        const resourceToUpdate = resources.find((resource) => resource.resourceId === selectedResourceId);

        if (!resourceToUpdate) return;

        try {
            const response = await fetch(`/api/resources/${selectedResourceId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    resourceId: resourceToUpdate.resourceId,
                    resourceName: resourceToUpdate.resourceName,
                    resourceType: resourceToUpdate.resourceType,
                    resourceQuantity: resourceToUpdate.resourceQuantity,
                    resourceStatus,
                    warehouseId: resourceToUpdate.warehouseId
                }),
            });

            if (!response.ok) {
                alert("Failed to update resource status");
                return;
            }

            alert("Resource status updated successfully");
        } catch (error) {
            console.error(error);
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
                {resources.length > 0 ? (
                    resources.map((resource) => (
                        <tr key={resource.resourceId} className="border-b border-gray-300">
                            <td className="px-4 py-2">{resource.resourceId}</td>
                            <td className="px-4 py-2">{resource.resourceName}</td>
                            <td className="px-4 py-2">{resource.resourceType}</td>
                            <td className="px-4 py-2">{resource.resourceQuantity}</td>
                            <td className="px-4 py-2">{resource.resourceStatus}</td>
                            <td className="px-4 py-2">{resource.warehouseId}</td>
                            <td className="px-4 py-2">
                                <button
                                    className="ml-2 bg-blue-500 text-white px-3 py-1 rounded"
                                    onClick={() => handleStatusChange(resource.resourceId, resource.resourceStatus)}
                                >
                                    Edit Status
                                </button>

                                {selectedResourceId === resource.resourceId && (
                                    <div className="mt-2">
                                        <select
                                            className="border border-gray-300 rounded px-2 py-1"
                                            value={selectedStatus}
                                            onChange={(e) => setSelectedStatus(e.target.value)}
                                        >
                                            <option value="PRZYDZIELONY">PRZYDZIELONY</option>
                                            <option value="NIEPRZYDZIELONY">NIEPRZYDZIELONY</option>
                                        </select>
                                        <button
                                            className="ml-2 bg-blue-500 text-white px-3 py-1 rounded"
                                            onClick={handleStatusUpdate}
                                            disabled={!selectedStatus}
                                        >
                                            Update
                                        </button>
                                    </div>
                                )}
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan={7} className="px-4 py-2 text-center text-gray-500">
                            No resources found.
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default ResourcesTable;
