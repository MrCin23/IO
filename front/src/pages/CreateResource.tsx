import ResourceForm from "@/components/ResourceForm";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Link } from "react-router";

const CreateResource = () => {
  return (
      <div className="min-h-screen flex items-center justify-center">
        <Card>
          <CardHeader>
            <CardTitle className="text-center">Create a New Resource</CardTitle>
            <CardDescription>
              Enter the resource name, type, quantity, status, warehouseId to add a new resource to the system.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ResourceForm />
          </CardContent>
          <CardFooter className="justify-center">
            <Link to="/resources">
              <span className="text-blue-700">Back to Resource List</span>
            </Link>
          </CardFooter>
        </Card>
      </div>
  );
};

export default CreateResource;