import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { FinancialNeedsList } from '../components/layouts/victim/FinancialNeedsList';
import { ManualNeedsList } from '../components/layouts/victim/ManualNeedsList';
import { MaterialNeedsList } from '../components/layouts/victim/MaterialNeedsList';
import AddNeedForm from '../components/layouts/victim/AddNeedForm';
import { getCurrentUser } from '../components/layouts/victim/components/NeedsListHelper';
import { useTranslation } from 'react-i18next';

export const VictimPage = () => {
  const { t } = useTranslation();
  const [activeList, setActiveList] = useState<'financial' | 'manual' | 'material' | 'form'>('form');
  const navigate = useNavigate();

  useEffect(() => {
    const checkUserRole = async () => {
      try {
        const token = Cookies.get('jwt');
        if (!token) {
          navigate('/');
          return;
        }

        const userInfo = await getCurrentUser(token);
        const role = userInfo.role.roleName;

        if (role !== 'POSZKODOWANY' && role !== 'PRZEDSTAWICIEL_WŁADZ') {
          navigate('/');
        }
      } catch (error) {
        console.error('Error checking user role:', error);
        navigate('/');
      }
    };

    checkUserRole();
  }, [navigate]);

  return (
    <div className="flex justify-center items-center overflow-auto">
      <div className="p-4">
        <div className="flex gap-4 mb-6">
          <button
            onClick={() => setActiveList('financial')}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {t('financialNeeds')}
          </button>
          <button
            onClick={() => setActiveList('manual')}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {t('manualNeeds')}
          </button>
          <button
            onClick={() => setActiveList('material')}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {t('materialNeeds')}
          </button>
          <button
            onClick={() => setActiveList('form')}
            className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            {t('addNeed')}
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
