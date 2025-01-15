import React, { useEffect, useState } from 'react';
import { MaterialNeed } from './type';
import Cookies from 'js-cookie';
import { useTranslation } from 'react-i18next';
import axios from 'axios';
import { StatusChangeButton } from './components/StatusChangeButton';
import { getCurrentUser } from './components/NeedsListHelper';
import { getPolishStatusLabel } from './statuses';

export const MaterialNeedsList: React.FC = () => {
    const { t } = useTranslation();

    const [needs, setNeeds] = useState<MaterialNeed[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    const fetchNeeds = async () => {
        try {
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error('No authentication token found');
            }
    
            const userInfo = await getCurrentUser(token);
            let endpoint = 'http://localhost:8080/api/material-needs';
    
            if (userInfo.role.roleName === 'POSZKODOWANY') {
                endpoint = `http://localhost:8080/api/material-needs/user/${userInfo.id}`;
            } else if (userInfo.role.roleName === 'PRZEDSTAWICIEL_WŁADZ') {
                endpoint = 'http://localhost:8080/api/material-needs';
            } else {
                throw new Error('Role not valid');
            }
    
            const response = await axios.get<MaterialNeed[]>(endpoint, {
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
        return <div className="text-center p-4 rounded-lg">{t('noMaterialNeeds')}</div>;
    }
    return (
        <div className="space-y-4">
            {needs
                .sort((a, b) => a.id - b.id)
                .map((need) => {
                    const expirationDate = new Date(need.expirationDate);
                    const formattedExpirationDate = expirationDate.toLocaleDateString();
                    return (
                        <div className="space-y-4">
                            {needs
                                .sort((a, b) => a.id - b.id)
                                .map((need) => (
                                <div key={need.id} className="p-4 border rounded-xl shadow-sm text-left bg-gray-100 text-black">
                                    <p className="mb-1">{t('id')}: {need.id}</p>
                                    <p className="mb-1">{t('description')}: {need.description}</p>
                                    <p className="mb-1">{t('expirationDate')}: {formattedExpirationDate}</p>
                                    <p className="mb-1">{t('itemCategory')}: {need.itemCategory}</p>
                                    <p className="mb-1">{t('status')}: {t(`statusLabels.${need.status}`)}</p>
                                    <StatusChangeButton
                                        needId={need.id}
                                        currentStatus={need.status}
                                        needType="material"
                                        onStatusChange={fetchNeeds}
                                    />
                                </div>
                            ))}
                        </div>
                    );
                })}
            </div>
        );          
};
