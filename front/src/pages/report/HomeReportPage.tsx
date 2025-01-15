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

export const HomeReportPage = () => {
    const [activeTab, setActiveTab] = useState('EmptyContent');

    const [visibleButtons, setVisibleButtons] = useState<string[]>([]); // Kontrolowanie widoczności przycisków

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
                        General
                    </Button>
                )}
                {visibleButtons.includes('Action') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionContent')}
                    >
                        Action
                    </Button>
                )}
                {visibleButtons.includes('ActionHistory') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionHistoryContent')}
                    >
                        Action History
                    </Button>
                )}
                {visibleButtons.includes('Modular') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ModularContent')}
                    >
                        Modular
                    </Button>
                )}
                {visibleButtons.includes('Summary') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('SummaryContent')}
                    >
                        Summary
                    </Button>
                )}
                {visibleButtons.includes('TransactionHistory') && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('TransactionHistoryContent')}
                    >
                        Transaction History
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
