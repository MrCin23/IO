import React, { useEffect, useState } from 'react';
import { ManualNeed } from './type';
import { StatusChangeButton } from './components/StatusChangeButton';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import Cookies from 'js-cookie';
import { getCurrentUser } from './components/NeedsListHelper';

export const ManualNeedsList: React.FC = () => {
    const { t } = useTranslation();

    const [needs, setNeeds] = useState<ManualNeed[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const fetchNeeds = async () => {
        try {
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error('No authentication token found');
            }
    
            const userInfo = await getCurrentUser(token);
            let endpoint = 'http://localhost:8080/api/manual-needs';
    
            if (userInfo.role.roleName === 'POSZKODOWANY') {
                endpoint = `http://localhost:8080/api/manual-needs/user/${userInfo.id}`;
            } else if (userInfo.role.roleName === 'PRZEDSTAWICIEL_WŁADZ') {
                endpoint = 'http://localhost:8080/api/manual-needs';
            } else {
                throw new Error('Role not valid');
            }
    
            const response = await axios.get<ManualNeed[]>(endpoint, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log(response.data);
            setNeeds(response.data);
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
        return <div>{t('loading')}...</div>;
    }

    if (error) {
        return <div>{t('error')}: {error}</div>;
    }

    if (needs.length === 0) {
        return <div className="text-center p-4 rounded-xl">{t('noManualNeeds')}</div>;
    }

    return (
        <div className="space-y-4">
            {needs
                .sort((a, b) => a.id - b.id)
                .map((need) => {
                    const expirationDate = new Date(need.expirationDate);
                    const formattedExpirationDate = expirationDate.toLocaleDateString();
    
                    return (
                        <div key={need.id} className="p-4 border rounded-lg shadow-sm text-left bg-gray-100 text-black">
                            <p className="mb-1">{t('id')}: {need.id}</p>
                            <p className="mb-1">{t('description')}: {need.description}</p>
                            <p className="mb-1">{t('expirationDate')}: {formattedExpirationDate}</p>
                            <p className="mb-1">{t('volunteers')}: {need.volunteers}/{need.maxVolunteers}</p>
                            <p className="mb-1">{t('status')}: {t(`statusLabels.${need.status}`)}</p>
                            <StatusChangeButton
                                needId={need.id}
                                currentStatus={need.status}
                                needType="manual"
                                onStatusChange={fetchNeeds}
                            />
                        </div>
                    );
                })}
        </div>
    );
};
