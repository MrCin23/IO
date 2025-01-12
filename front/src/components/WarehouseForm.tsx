import { useForm } from "react-hook-form";
import { Button } from "@/components/ui/button";
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useToast } from "@/hooks/use-toast";
import { useNavigate } from "react-router";

export interface Warehouse {
    warehouseId?: number;
    warehouseName: string;
    location: string;
}

const WarehouseForm = () => {
    const { toast } = useToast();
    const navigate = useNavigate();
    const form = useForm<Warehouse>({
        defaultValues: {
            warehouseName: "",
            location: "",
        },
    });

    const onSubmit = async (values: Warehouse) => {
        const response = await fetch("/api/warehouses", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(values),
        });

        const result = await response.json();
        if (!response.ok) {
            toast({
                title: "Error",
                description: result.message || "Failed to add warehouse",
                variant: "destructive",
            });
            return;
        }

        toast({
            title: "Success",
            description: "Warehouse added successfully",
        });

        // Redirect to the warehouse list or another page
        navigate("/warehouses");
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                <FormField
                    control={form.control}
                    name="warehouseName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Warehouse Name</FormLabel>
                            <FormControl>
                                <Input placeholder="Enter warehouse name" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="location"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Location</FormLabel>
                            <FormControl>
                                <Input placeholder="Enter warehouse location" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <div className="flex justify-center">
                    <Button type="submit">Add Warehouse</Button>
                </div>
            </form>
        </Form>
    );
};

export default WarehouseForm;
