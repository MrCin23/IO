import React, { useState } from 'react';
import { MaterialNeedsList } from './MaterialNeedsList';
import { ManualNeedsList } from './ManualNeedsList';
import { FinancialNeedsList } from './FinancialNeedsList';

const Poszkodowani: React.FC = () => {
    const [activeList, setActiveList] = useState<'financial' | 'manual' | 'material' | null>(null);

    return (
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
            </div>

            {activeList === 'financial' && <FinancialNeedsList />}
            {activeList === 'manual' && <ManualNeedsList />}
            {activeList === 'material' && <MaterialNeedsList />}
        </div>
    );
};

export default Poszkodowani;
