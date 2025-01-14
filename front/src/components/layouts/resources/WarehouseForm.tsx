import { useForm } from "react-hook-form";
import { useNavigate, useLocation } from "react-router-dom";
import api from "../../../api/Axios.tsx";

export interface Warehouse {
    warehouseId?: number;
    warehouseName: string;
    location: string;
}

const WarehouseForm = () => {
    const form = useForm<Warehouse>({
        defaultValues: {
            warehouseName: "",
            location: "",
        },
    });

    const navigate = useNavigate();
    const location = useLocation();

    const onSubmit = async (values: Warehouse) => {
        try {
            const response = await api.post("/warehouses", values);

            if (response.status === 201) {
                alert("Warehouse added successfully");
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
                alert("Failed to add warehouse. Please try again.");
            }
        } catch (error) {
            console.error("An unexpected error occurred:", error);
            alert("An unexpected error occurred. Please try again.");
        }
    };

    return (
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6 max-w-lg mx-auto">
            <div className="flex flex-col">
                <label htmlFor="warehouseName" className="text-lg font-semibold text-gray-700">
                    Warehouse Name
                </label>
                <input
                    id="warehouseName"
                    {...form.register("warehouseName", { required: "Warehouse name is required" })}
                    className="mt-2 p-3 border border-gray-300 rounded-md"
                    placeholder="Enter warehouse name"
                />
                {form.formState.errors.warehouseName && (
                    <span className="text-red-600 text-sm mt-1">
                        {form.formState.errors.warehouseName.message}
                    </span>
                )}
            </div>

            <div className="flex flex-col">
                <label htmlFor="location" className="text-lg font-semibold text-gray-700">
                    Location
                </label>
                <input
                    id="location"
                    {...form.register("location", { required: "Location is required" })}
                    className="mt-2 p-3 border border-gray-300 rounded-md"
                    placeholder="Enter warehouse location"
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
                    Add Warehouse
                </button>
            </div>
        </form>
    );
};

export default WarehouseForm;
