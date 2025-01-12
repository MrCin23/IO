import { Resource } from "@/types";

const ResourcesTable = ({ resources }: { resources: Resource[] }) => {
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
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td
                            colSpan={6}
                            className="px-4 py-2 text-center text-gray-500"
                        >
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
