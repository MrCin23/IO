import React from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';

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
            
            if (response.status === 200) {
                onStatusChange();
            }
        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    const availableTransitions = allowedTransitions[currentStatus];

    return (
        <div className="flex gap-2 mt-2">
            {availableTransitions.map(newStatus => (
                newStatus === "CANCELLED" ? null : (
                    <button
                        key={newStatus}
                        onClick={() => handleStatusChange(newStatus)}
                        className={`px-3 py-1 text-white rounded text-sm ${statusColors[newStatus]}`}
                    >
                        Change to: {newStatus}
                    </button>
                )
            ))}
            
            {availableTransitions.includes("CANCELLED") && (
                <button
                    onClick={() => handleStatusChange("CANCELLED")}
                    className="px-3 py-1 text-white rounded text-sm bg-red-500 hover:bg-red-600"
                >
                    Cancel
                </button>
            )}
        </div>
    );
};
