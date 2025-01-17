import React, { useEffect, useState } from 'react';
import { FinancialNeed } from './type';
import Cookies from 'js-cookie';
import { StatusChangeButton } from './components/StatusChangeButton';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import { getCurrentUser } from './components/NeedsListHelper';

export const FinancialNeedsList: React.FC = () => {
    const { t } = useTranslation();

    const [needs, setNeeds] = useState<FinancialNeed[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const fetchNeeds = async () => {
        try {
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error('No authentication token found');
            }

            const userInfo = await getCurrentUser(token);
            let endpoint = 'http://localhost:8080/api/financial-needs';

            if (userInfo.role.roleName === 'POSZKODOWANY') {
                endpoint = `http://localhost:8080/api/financial-needs/user/${userInfo.id}`;
            } else if (userInfo.role.roleName === 'PRZEDSTAWICIEL_WŁADZ') {
                endpoint = 'http://localhost:8080/api/financial-needs';
            } else {
                throw new Error('Role not valid');
            }

            const response = await axios.get<FinancialNeed[]>(endpoint, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setNeeds(response.data);
            console.log(response.data);
        } catch (error) {
            if (error instanceof Error) {
                setError(error.message);
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchNeeds();
    }, []);

    if (loading) {
        return <div>{t('victim.loading')}:...</div>;
    }

    if (error) {
        return <div>{t('victim.error')}: {error}</div>;
    }

    if (needs.length === 0) {
        return <div className="text-center p-4 rounded-xl">{t('victim.noFinancialNeeds')}</div>;
    }

    return (
        <div className="space-y-4">
            {needs.sort((a, b) => a.id - b.id).map((need) => {
                const expirationDate = new Date(need.expirationDate);
                const formattedExpirationDate = expirationDate.toLocaleDateString();
                const creationDate = new Date(need.creationDate);
                const formattedCreationDate = creationDate.toLocaleDateString();
                return (
                    <div key={need.id} className="p-4 border rounded-lg shadow-sm text-left bg-gray-100 text-black">
                        <p className="mb-1"><strong>{t('victim.id')}:</strong> {need.id}</p>
                        <p className="mb-1"><strong>{t('victim.description')}:</strong> {need.description}</p>
                        <p className="mb-1"><strong>{t('victim.collected')}:</strong> {need.collectionStatus} / {need.collectionGoal} PLN</p>
                        <p className="mb-1"><strong>{t('victim.creationDate')}:</strong> {formattedCreationDate}</p>
                        <p className="mb-1"><strong>{t('victim.expirationDate')}:</strong> {formattedExpirationDate}</p>
                        <p className="mb-1"><strong>{t('victim.status')}:</strong> {t(`statusLabels.${need.status}`)}</p>
                        <StatusChangeButton
                            needId={need.id}
                            currentStatus={need.status}
                            needType="financial"
                            onStatusChange={fetchNeeds}
                        />
                    </div>
                );
            })}
        </div>
    );
};