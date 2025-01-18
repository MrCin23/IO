import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useTranslation } from 'react-i18next';
import { getCurrentUser } from './NeedsListHelper';

type Status = "IN_PROGRESS" | "PENDING" | "COMPLETED" | "CANCELLED";

interface StatusChangeButtonProps {
    needId: number;
    currentStatus: Status;
    needType: 'material' | 'manual' | 'financial';
    onStatusChange: () => void;
}

export const StatusChangeButton: React.FC<StatusChangeButtonProps> = ({
    needId,
    currentStatus,
    needType,
    onStatusChange
}) => {
    const { t } = useTranslation();
    const [userRole, setUserRole] = useState<string | null>(null);

    useEffect(() => {
        const fetchUserRole = async () => {
            try {
                const token = Cookies.get('jwt');
                if (!token) {
                    throw new Error('No authentication token found');
                }
                const userInfo = await getCurrentUser(token);
                setUserRole(userInfo.role.roleName);
            } catch (error) {
                console.error('Error fetching user role:', error);
            }
        };

        fetchUserRole();
    }, []);

    const allowedTransitions: Record<Status, Status[]> = {
        "PENDING": ["CANCELLED", "IN_PROGRESS"],
        "IN_PROGRESS": ["COMPLETED"],
        "COMPLETED": [],
        "CANCELLED": []
    };

    const statusColors: Record<Status, string> = {
        "IN_PROGRESS": "bg-yellow-500 hover:bg-yellow-600",
        "PENDING": "bg-blue-500 hover:bg-blue-600",
        "COMPLETED": "bg-green-500 hover:bg-green-600",
        "CANCELLED": "bg-red-500 hover:bg-red-600"
    };

    const handleStatusChange = async (newStatus: Status) => {
        try {
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error('No authentication token found');
            }

            const response = await axios.patch(
                `http://localhost:8080/api/${needType}-needs/status/${needId}?status=${newStatus}`,
                {}, // empty body for PATCH request
                {
                    headers: { Authorization: `Bearer ${token}` }
                }
            );

            const getRes =  await axios.get(
                `http://localhost:8080/api/${needType}-needs/${needId}`,
                {
                    headers: { Authorization: `Bearer ${token}` }
                }
            ); //Podbranie lokacji zgłoszenia

            await axios.put(
                `http://localhost:8080/api/map/status/${getRes.data.mapPointId}/false`,
                {},
                {
                    headers: { Authorization: `Bearer ${token}` }
                }//Archiwizacja danej lokacji
            );

            if (response.status === 200) {
                onStatusChange();

            }


        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    const availableTransitions = allowedTransitions[currentStatus];

    return userRole === 'POSZKODOWANY' ? (
        <div className="flex gap-2 mt-2">
            {availableTransitions.includes("CANCELLED") && (
                <button
                    onClick={() => handleStatusChange("CANCELLED")}
                    className="px-3 py-1 text-white rounded text-sm bg-red-500 hover:bg-red-600"
                >
                    {t('victim.buttonStatusLabels.CANCELLED')}
                </button>
            )}
        </div>
    ) : (
        <div className="flex gap-2 mt-2">
            {availableTransitions.map(newStatus => (
                newStatus === "CANCELLED" ? null : (
                    <button
                        key={newStatus}
                        onClick={() => handleStatusChange(newStatus)}
                        className={`px-3 py-1 text-white rounded text-sm ${statusColors[newStatus]}`}
                    >
                        {t(`victim.buttonStatusLabels.${newStatus}`)}
                    </button>
                )
            ))}

            {availableTransitions.includes("CANCELLED") && (
                <button
                    onClick={() => handleStatusChange("CANCELLED")}
                    className="px-3 py-1 text-white rounded text-sm bg-red-500 hover:bg-red-600"
                >
                    {t('victim.buttonStatusLabels.CANCELLED')}
                </button>
            )}
        </div>
    );
};
