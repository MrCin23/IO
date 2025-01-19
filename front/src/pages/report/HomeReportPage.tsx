import { useState, useEffect } from 'react';
import { Button, Box } from '@mui/material';
import { jwtDecode } from "jwt-decode";
import { Empty } from './Empty';
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
                throw new Error(t('jwtMissing'));
            }

            const decoded = jwtDecode(token);

            // Ustaw widoczność przycisków w zależności od roli użytkownika
            const role = decoded.role; // Odczytaj nazwę roli
            let buttons: string[];
            switch (role) {
                case 'ROLE_PRZEDSTAWICIEL_WŁADZ':
                    buttons = ['Summary', 'Modular'];
                    break;
                case 'ROLE_ORGANIZACJA_POMOCOWA':
                    buttons = ['Summary', 'Modular', 'TransactionHistory'];
                    break;
                case 'ROLE_DARCZYŃCA':
                    buttons = ['TransactionHistory'];
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
                    position: 'relative',
                    width: '100%',
                    left: '0'
                }}
            >
                {visibleButtons.includes('Modular') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ModularContent')}
                    >
                        {t('report.modular')}
                    </Button>
                )}
                {visibleButtons.includes('Summary') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('SummaryContent')}
                    >
                        {t('report.summary')}
                    </Button>
                )}
                {visibleButtons.includes('TransactionHistory') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('TransactionHistoryContent')}
                    >
                        {t('report.transactionHistory')}
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
