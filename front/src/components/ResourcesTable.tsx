import {
    Table,
    TableBody, TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import {Resource} from "@/types";

const ResourcesTable = ({resources}:{resources: Resource[]}) => {
    return (
        <Table>
            <TableCaption>A list of resources.</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">Resource ID</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Type</TableHead>
                    <TableHead>Quantity</TableHead>
                    <TableHead>Status</TableHead>
                    <TableHead>Warehouse ID</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {resources.length > 0 ? (
                    resources.map((resource) => (
                        <TableRow key={resource.resourceId}>
                            <TableCell>{resource.resourceId}</TableCell>
                            <TableCell>{resource.resourceName}</TableCell>
                            <TableCell>{resource.resourceType}</TableCell>
                            <TableCell>{resource.resourceQuantity}</TableCell>
                            <TableCell>{resource.resourceStatus}</TableCell>
                            <TableCell>{resource.warehouseId}</TableCell>
                        </TableRow>
                    ))
                ) : (
                    <TableRow>
                        <TableCell colSpan={6}>No resources found.</TableCell>
                    </TableRow>
                )}
            </TableBody>
        </Table>
    );
}

export default ResourcesTable;