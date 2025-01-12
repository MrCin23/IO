import WarehouseForm from "@/components/WarehouseForm";
import { Link } from "react-router-dom";

const CreateWarehouse = () => {
    return (
        <div className="min-h-screen flex items-center justify-center p-4">
            <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-2xl">
                <div className="text-center">
                    <h1 className="text-3xl font-semibold">Create a New Warehouse</h1>
                    <p className="mt-2 text-gray-600">
                        Enter the warehouse name and location to add a new warehouse to the system.
                    </p>
                </div>
                <div className="mt-8">
                    <WarehouseForm />
                </div>
                <div className="mt-6 flex justify-center">
                    <Link to="/warehouses" className="text-blue-700 hover:underline">
                        Back to Warehouse List
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default CreateWarehouse;
