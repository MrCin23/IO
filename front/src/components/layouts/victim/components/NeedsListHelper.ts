import axios from 'axios';

interface UserInfo {
    id: number;
    role: {
        roleName: string;
    };
}

export const getCurrentUser = async (token: string): Promise<UserInfo> => {
    try {
        const response = await axios.get<UserInfo>('http://localhost:8080/api/auth/me', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};