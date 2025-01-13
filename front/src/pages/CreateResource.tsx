import ResourceForm from "@/components/ResourceForm";
import { Link } from "react-router";

const CreateResource = () => {
  return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="max-w-lg w-full bg-white shadow-md rounded p-6">
          <div className="text-center mb-6">
            <h1 className="text-2xl font-semibold">Create a New Resource</h1>
            <p className="text-sm text-gray-600">
              Enter the resource name, type, quantity, and warehouse to add a new resource to the system.
            </p>
          </div>
          <div className="mb-6">
            <ResourceForm />
          </div>
          <div className="text-center">
            <Link to="/resources" className="text-blue-700 hover:underline">
              Back to Resource List
            </Link>
          </div>
        </div>
      </div>
  );
};

export default CreateResource;
