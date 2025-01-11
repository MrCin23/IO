import WarehouseForm from "@/components/WarehouseForm";
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Link } from "react-router";

const CreateWarehouse = () => {
    return (
        <div className="min-h-screen flex items-center justify-center">
            <Card>
                <CardHeader>
                    <CardTitle className="text-center">Create a New Warehouse</CardTitle>
                    <CardDescription>
                        Enter the warehouse name and location to add a new warehouse to the system.
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <WarehouseForm />
                </CardContent>
                <CardFooter className="justify-center">
                    <Link to="/warehouses">
                        <span className="text-blue-700">Back to Warehouse List</span>
                    </Link>
                </CardFooter>
            </Card>
        </div>
    );
};

export default CreateWarehouse;