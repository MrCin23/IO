import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import Cookies from 'js-cookie';
import axios from '../../api/Axios';
import { Account } from '../../models/uwierzytelnianie/Account';

interface AccountContextType {
    account: Account | null;
    login: (accountInfo: Account) => void;
    logout: () => void;
}

const AccountContext = createContext<AccountContextType | undefined>(undefined);

interface AccountProviderProps {
    children: ReactNode;
}

export const AccountProvider: React.FC<AccountProviderProps> = ({ children }) => {
    const [account, setAccount] = useState<Account | null>(null);

    const fetchAccountData = async () => {
        const token = Cookies.get('jwt');
        if (token) {
            try {
                const response = await axios.get('/auth/me', {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setAccount(response.data);
            } catch (error) {
                console.error('Failed to fetch account:', error);
                logout();
            }
        }
    };

    useEffect(() => {
        fetchAccountData();
    }, []);

    const login = (accountInfo: Account) => setAccount(accountInfo);

    const logout = () => {
        setAccount(null);
        Cookies.remove('jwt');
        Cookies.remove('refreshToken');
    };

    return (
        <AccountContext.Provider value={{ account, login, logout }}>
            {children}
        </AccountContext.Provider>
    );
};

export const useAccount = (): AccountContextType => {
    const context = useContext(AccountContext);
    if (context === undefined) {
        throw new Error('useAccount must be used within an AccountProvider');
    }
    return context;
};