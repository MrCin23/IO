import WarehouseTable from '../components/layouts/resources/WarehousesTable.tsx';
import { Warehouse } from '../types/index';
import { useState, useEffect } from 'react';

export const Warehouses = () => {
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);

    useEffect(() => {
        const fetchWarehouses = async () => {
            const response = await fetch("/api/warehouses");
            const result = await response.json();
            setWarehouses(result);
        }
        fetchWarehouses();
    }, []);

    return (
        <div>
            <h1 className="text-bold text-5xl text-center my-8">List of Warehouses</h1>
            <WarehouseTable warehouses={warehouses}/>
        </div>
    );
};
