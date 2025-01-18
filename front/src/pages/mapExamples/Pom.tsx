import MapView from "../../components/mapComponent/MapView.tsx";
import {useEffect, useState} from 'react';
import Cookies from 'js-cookie';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {Account} from "@/models/uwierzytelnianie/Account.tsx";

export const Pom = () =>
{
    const navigate = useNavigate();
    const [user, setUser] = useState<Account | null>(null);
    const getMe = async (token: string): Promise<Account> => {
        try {
            const response = await axios.get<Account>('http://localhost:8080/api/auth/me', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            setUser(response.data);
            return response.data;
        } catch (error) {
            console.error('Error getting user:', error);
            navigate('/');
        }
    };
    useEffect(() => {
        const checkUserRole = async () => {
                try {
                    const token = Cookies.get('jwt');
                    if (!token) {
                        navigate('/');
                        return;
                    }

                    const response = await getMe(token);
                    setUser(response);
                    const role = response!.role.roleName;
                    if (role !== 'WOLONTARIUSZ') {
                        navigate('/');
                    }
                } catch (error) {
                    console.error('Error checking user role:', error);
                    navigate('/');
                }
        };

        checkUserRole();
    }, [navigate]);


    return(
    <div>SKPH - home page
        <MapView pointType={"VOLUNTEER"} canAddPoints={true} canShowPoints={true} pointOwner={user!}></MapView>
    </div>);
    //todo do wywalenia
}
