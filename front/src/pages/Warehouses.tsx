import WarehouseTable from '../components/layouts/resources/WarehousesTable.tsx';
import { Warehouse } from '../types/index';
import { useState, useEffect } from 'react';
import api from "../api/Axios.tsx"

const Warehouses = () => {
    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);

    useEffect(() => {
        const fetchWarehouses = async () => {
            try {
                const response = await api.get("/warehouses");
                setWarehouses(response.data);
            } catch (error) {
                console.error("Failed to fetch warehouses:", error);
            }
        };

        fetchWarehouses();
    }, []);

    return (
        <div className="min-h-screen">
            <WarehouseTable warehouses={warehouses}/>
        </div>
    );
};

export default Warehouses;