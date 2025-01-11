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
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useToast } from "@/hooks/use-toast";
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
    const { toast } = useToast();
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
                toast({
                    title: "Error",
                    description: "Failed to load warehouses.",
                    variant: "destructive",
                });
            }
        };

        fetchWarehouses();
    }, [toast]);

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
                toast({
                    title: "Error",
                    description: result.message || "Failed to add resource",
                    variant: "destructive",
                });
                return;
            }

            toast({
                title: "Success",
                description: "Resource added successfully",
            });

            navigate("/resources");
        } catch (error) {
            console.error(error);
            toast({
                title: "Error",
                description: "An unexpected error occurred.",
                variant: "destructive",
            });
        }
    };

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                <FormField
                    control={form.control}
                    name="resourceName"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Resource Name</FormLabel>
                            <FormControl>
                                <Input placeholder="Enter resource name" {...field} />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="resourceType"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Resource Type</FormLabel>
                            <FormControl>
                                <Select
                                    onValueChange={field.onChange}
                                    defaultValue={field.value}
                                >
                                    <SelectTrigger>
                                        <SelectValue placeholder="Select resource type" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectItem value="Food">Food</SelectItem>
                                        <SelectItem value="Toys">Toys</SelectItem>
                                        <SelectItem value="Items">Items</SelectItem>
                                    </SelectContent>
                                </Select>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="resourceQuantity"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Quantity</FormLabel>
                            <FormControl>
                                <Input
                                    type="number"
                                    placeholder="Enter quantity"
                                    {...field}
                                />
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <FormField
                    control={form.control}
                    name="warehouseId"
                    render={({ field }) => (
                        <FormItem>
                            <FormLabel>Warehouse</FormLabel>
                            <FormControl>
                                <Select
                                    onValueChange={field.onChange}
                                    defaultValue={String(field.value)}
                                >
                                    <SelectTrigger>
                                        <SelectValue placeholder="Select warehouse" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        {warehouses.map((warehouse) => (
                                            <SelectItem key={warehouse.warehouseId} value={String(warehouse.warehouseId)}>
                                                {warehouse.warehouseName} ({warehouse.location})
                                            </SelectItem>
                                        ))}
                                    </SelectContent>
                                </Select>
                            </FormControl>
                            <FormMessage />
                        </FormItem>
                    )}
                />
                <div className="flex justify-center">
                    <Button type="submit">Add Resource</Button>
                </div>
            </form>
        </Form>
    );
};

export default ResourceForm;
