import { useForm } from "react-hook-form";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";

export interface Resource {
    resourceId?: number;
    resourceName: string;
    resourceType: string;
    resourceQuantity: number;
    resourceStatus: string;
    warehouseId: number;
}

const ResourceForm = () => {
    const navigate = useNavigate();
    const [warehouses, setWarehouses] = useState<{ warehouseId: number; warehouseName: string; location: string }[]>([]);
    const form = useForm<Resource>({
        defaultValues: {
            resourceName: "",
            resourceType: "",
            resourceQuantity: 0,
            resourceStatus: "NIEPRZYDZIELONY",
            warehouseId: 1,
        },
    });

    useEffect(() => {
        const fetchWarehouses = async () => {
            try {
                const response = await fetch("/api/warehouses");
                if (!response.ok) {
                    throw new Error("Failed to fetch warehouses");
                }
                const data = await response.json();
                setWarehouses(data);
            } catch (error) {
                console.error(error);

            }
        };

        fetchWarehouses();
    }, []);

    const onSubmit = async (values: Resource) => {
        try {
            const response = await fetch("/api/resources", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(values),
            });

            const result = await response.json();
            if (!response.ok) {
                console.log(result);
            }

            navigate("/resources");
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="max-w-lg mx-auto p-6 bg-white shadow-md rounded">
            <h2 className="text-xl font-semibold text-center mb-4">Add Resource</h2>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
                <div>
                    <label htmlFor="resourceName" className="block text-sm font-medium text-gray-700">
                        Resource Name
                    </label>
                    <input
                        id="resourceName"
                        {...form.register("resourceName", { required: "Resource name is required" })}
                        placeholder="Enter resource name"
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-blue-200"
                    />
                    {form.formState.errors.resourceName && (
                        <p className="text-sm text-red-600 mt-1">{form.formState.errors.resourceName.message}</p>
                    )}
                </div>

                <div>
                    <label htmlFor="resourceType" className="block text-sm font-medium text-gray-700">
                        Resource Type
                    </label>
                    <select
                        id="resourceType"
                        {...form.register("resourceType", { required: "Resource type is required" })}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-blue-200"
                    >
                        <option value="" disabled>
                            Select resource type
                        </option>
                        <option value="Food">Food</option>
                        <option value="Toys">Toys</option>
                        <option value="Items">Items</option>
                    </select>
                    {form.formState.errors.resourceType && (
                        <p className="text-sm text-red-600 mt-1">{form.formState.errors.resourceType.message}</p>
                    )}
                </div>

                <div>
                    <label htmlFor="resourceQuantity" className="block text-sm font-medium text-gray-700">
                        Quantity
                    </label>
                    <input
                        id="resourceQuantity"
                        type="number"
                        {...form.register("resourceQuantity", { required: "Quantity is required" })}
                        placeholder="Enter quantity"
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-blue-200"
                    />
                    {form.formState.errors.resourceQuantity && (
                        <p className="text-sm text-red-600 mt-1">{form.formState.errors.resourceQuantity.message}</p>
                    )}
                </div>

                <div>
                    <label htmlFor="warehouseId" className="block text-sm font-medium text-gray-700">
                        Warehouse
                    </label>
                    <select
                        id="warehouseId"
                        {...form.register("warehouseId", { required: "Warehouse is required" })}
                        className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded focus:outline-none focus:ring focus:ring-blue-200"
                    >
                        {warehouses.map((warehouse) => (
                            <option key={warehouse.warehouseId} value={warehouse.warehouseId}>
                                {warehouse.warehouseName} ({warehouse.location})
                            </option>
                        ))}
                    </select>
                    {form.formState.errors.warehouseId && (
                        <p className="text-sm text-red-600 mt-1">{form.formState.errors.warehouseId.message}</p>
                    )}
                </div>

                <div className="flex justify-center">
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-600 text-white rounded shadow hover:bg-blue-700 focus:outline-none focus:ring focus:ring-blue-200"
                    >
                        Add Resource
                    </button>
                </div>
            </form>
        </div>
    );
};

export default ResourceForm;
