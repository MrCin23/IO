import { useState } from 'react';
import { FinancialNeedsList } from '../components/layouts/victim/FinancialNeedsList';
import { ManualNeedsList } from '../components/layouts/victim/ManualNeedsList';
import { MaterialNeedsList } from '../components/layouts/victim/MaterialNeedsList';
import AddNeedForm from '../components/layouts/victim/AddNeedForm';

export const VictimPage = () => {
    const [activeList, setActiveList] = useState<'financial' | 'manual' | 'material' | 'form'>('form');

    return (

        <div className="flex justify-center items-center overflow-auto">
            <div className="p-4">
            <div className="flex gap-4 mb-6">
                <button 
                onClick={() => setActiveList('financial')}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                Financial Needs
                </button>
                <button 
                onClick={() => setActiveList('manual')}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                Manual Needs
                </button>
                <button 
                onClick={() => setActiveList('material')}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                Material Needs
                </button>
                <button 
                onClick={() => setActiveList('form')}
                className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                Add Need
                </button>
            </div>

            {activeList === 'financial' && <FinancialNeedsList />}
            {activeList === 'manual' && <ManualNeedsList />}
            {activeList === 'material' && <MaterialNeedsList />}
            {activeList === 'form' && <AddNeedForm />}

            </div>
        </div>
    );
};

export default VictimPage;
