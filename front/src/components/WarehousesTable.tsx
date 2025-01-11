import {
    Table,
    TableBody, TableCaption,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table";
import {Warehouse} from "@/types";

const WarehousesTable = ({warehouses}:{warehouses: Warehouse[]}) => {
    return (
        <Table>
            <TableCaption>A list of warehouses.</TableCaption>
            <TableHeader>
                <TableRow>
                    <TableHead className="w-[100px]">Warehouse ID</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Location</TableHead>
                </TableRow>
            </TableHeader>
            <TableBody>
                {warehouses.length > 0 ? (
                    warehouses.map((warehouse) => (
                        <TableRow key={warehouse.warehouseId}>
                            <TableCell>{warehouse.warehouseId}</TableCell>
                            <TableCell>{warehouse.warehouseName}</TableCell>
                            <TableCell>{warehouse.location}</TableCell>
                        </TableRow>
                    ))
                ) : (
                    <TableRow>
                        <TableCell colSpan={6}>No warehouses found.</TableCell>
                    </TableRow>
                )}
            </TableBody>
        </Table>
    );
}

export default WarehousesTable;