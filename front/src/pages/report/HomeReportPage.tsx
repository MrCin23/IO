import { useState, useEffect } from 'react';
import { Button, Box } from '@mui/material';
import axios from 'axios';
import { Empty } from './Empty';
import { General } from './General';
import { Action } from './Action';
import { ActionHistory } from './ActionHistory';
import { Modular } from './Modular';
import { Summary } from './Summary';
import { TransactionHistory } from './TransactionHistory';
import Cookies from "js-cookie";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";

export const HomeReportPage = () => {
    const [activeTab, setActiveTab] = useState('EmptyContent');

    const [visibleButtons, setVisibleButtons] = useState<string[]>([]); // Kontrolowanie widoczności przycisków
    const navigate = useNavigate();
    const { t } = useTranslation();

    const renderContent = () => {
        switch (activeTab) {
            case 'EmptyContent':
                return <Empty />;
            case 'GeneralContent':
                return <General />;
            case 'ActionContent':
                return <Action />;
            case 'ActionHistoryContent':
                return <ActionHistory />;
            case 'ModularContent':
                return <Modular />;
            case 'SummaryContent':
                return <Summary />;
            case 'TransactionHistoryContent':
                return <TransactionHistory />;
            default:
                return null;
        }
    };

    useEffect(() => {
        const fetchUser = async () => {
            const token = Cookies.get('jwt');
            if (!token) {
                navigate('/');
                throw new Error('Brak tokenu JWT');
            }

            const response = await axios.get(`http://localhost:8080/api/auth/me`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            const userData = response.data;

            // Ustaw widoczność przycisków w zależności od roli użytkownika
            const role = userData.role.roleName; // Odczytaj nazwę roli
            let buttons: string[];
            switch (role) {
                case 'PRZEDSTAWICIEL_WŁADZ':
                    buttons = ['General', 'Summary', 'Action', 'Modular'];
                    break;
                case 'ORGANIZACJA_POMOCOWA':
                    buttons = ['General', 'Summary', 'Action', 'Modular', 'TransactionHistory'];
                    break;
                case 'WOLONTARIUSZ':
                    buttons = ['ActionHistory'];
                    break;
                case 'DARCZYŃCA':
                    buttons = ['TransactionHistory'];
                    break;
                case 'POSZKODOWANY':
                    buttons = [];
                    break;
                default:
                    buttons = [];
            }
            setVisibleButtons(buttons);
        };

        fetchUser();
    }, []);

    return (
        <Box sx={{ p: 0, m: 0 }}>
            {/* Przyciski nawigacyjne */}
            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    gap: 2,
                    mt: 0,
                    position: 'fixed',
                    width: '100%',
                    left: '0',
                    top: 72,
                }}
            >
                {visibleButtons.includes('General') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('GeneralContent')}
                    >
                        {t('general')}
                    </Button>
                )}
                {visibleButtons.includes('Action') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionContent')}
                    >
                        {t('action')}
                    </Button>
                )}
                {visibleButtons.includes('ActionHistory') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionHistoryContent')}
                    >
                        {t('actionHistory')}
                    </Button>
                )}
                {visibleButtons.includes('Modular') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ModularContent')}
                    >
                        {t('modular')}
                    </Button>
                )}
                {visibleButtons.includes('Summary') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('SummaryContent')}
                    >
                        {t('summary')}
                    </Button>
                )}
                {visibleButtons.includes('TransactionHistory') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('TransactionHistoryContent')}
                    >
                        {t('transactionHistory')}
                    </Button>
                )}
            </Box>

            {/* Treść dynamiczna */}
            <Box sx={{ mt: '64px', p: 2 }}>
                {renderContent()}
            </Box>
        </Box>
    );
};
