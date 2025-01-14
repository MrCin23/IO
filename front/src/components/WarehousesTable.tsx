import { Warehouse } from "@/types";

const WarehousesTable = ({ warehouses }: { warehouses: Warehouse[] }) => {
    return (
        <div className="overflow-x-auto">
            <table className="min-w-full table-auto border-collapse">
                <caption className="text-xl font-semibold text-center py-4">A list of warehouses.</caption>
                <thead>
                <tr className="bg-gray-100">
                    <th className="px-4 py-2 text-left border">Warehouse ID</th>
                    <th className="px-4 py-2 text-left border">Name</th>
                    <th className="px-4 py-2 text-left border">Location</th>
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
                        <td colSpan={3} className="px-4 py-2 text-center">No warehouses found.</td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
};

export default WarehousesTable;
