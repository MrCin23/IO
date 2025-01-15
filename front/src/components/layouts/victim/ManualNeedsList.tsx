import React, { useEffect, useState } from 'react';
import { ManualNeed } from './type';
import { StatusChangeButton } from './components/StatusChangeButton';
import axios from 'axios';
import Cookies from 'js-cookie';
import { getCurrentUser } from './components/NeedsListHelper';

export const ManualNeedsList: React.FC = () => {
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
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (needs.length === 0) {
        return <div className="text-center p-4 rounded-xl">No manual needs found</div>;
    }

    return (
        <div className="space-y-4">
            {needs
                .sort((a, b) => a.id - b.id)
                .map((need) => (
                    <div key={need.id} className="p-4 border rounded-lg shadow-sm text-left bg-gray-100 text-black">
                        <p className="mb-1">ID: {need.id}</p>
                        <p className="mb-1">Description: {need.description}</p>
                        <p className="mb-1">Volunteers: {need.volunteers}/{need.maxVolunteers}</p>
                        <p className="mb-1">Status: {need.status}</p>
                        <p className="mb-1">Priority: {need.priority}</p>
                        <StatusChangeButton
                            needId={need.id}
                            currentStatus={need.status}
                            needType="manual"
                            onStatusChange={fetchNeeds}
                        />
                    </div>
                ))}
        </div>
    );
};
