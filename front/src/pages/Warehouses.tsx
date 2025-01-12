import WarehouseTable from '@/components/WarehousesTable.tsx';
import { Warehouse } from '@/types';
import { useState, useEffect } from 'react';

const Resources = () => {
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

export default Resources;