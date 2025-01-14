import { useState } from 'react';
import { Button, Box } from '@mui/material';
import { General } from './General';
import { Action } from './Action';
import { ActionHistory } from './ActionHistory';
import { Modular } from './Modular';
import { Summary } from './Summary';
import { TransactionHistory } from './TransactionHistory';

export const HomeReportPage = () => {

    const [activeTab, setActiveTab] = useState('GeneralContent');

    const renderContent = () => {
        switch (activeTab) {
            case 'GeneralContent':
                return <General/>;
            case 'ActionContent':
                return <Action/>;
            case 'ActionHistoryContent':
                return <ActionHistory/>;
            case 'ModularContent':
                return <Modular/>;
            case 'SummaryContent':
                return <Summary/>;
            case 'TransactionHistoryContent':
                return <TransactionHistory/>;
            default:
                return null;
        }
    };

    const [isVisibleG] = useState(true);
    const [isVisibleA] = useState(true);
    const [isVisibleAH] = useState(true);
    const [isVisibleM] = useState(true);
    const [isVisibleS] = useState(true);
    const [isVisibleTH] = useState(true);

    return (
        <Box sx={{ p: 0, m: 0 }}>
            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    gap: 2,
                    mt: 0,
                    position: 'fixed',
                    width: '100%',
                    left: '0',
                    top: 72
                }}
            >
                {/* Osobne przyciski */}
                {isVisibleG && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('GeneralContent')}
                    >
                        General
                    </Button>
                )}
                {isVisibleA && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionContent')}
                    >
                        Action
                    </Button>
                )}
                {isVisibleAH && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ActionHistoryContent')}
                    >
                        Action History
                    </Button>
                )}
                {isVisibleM && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('ModularContent')}
                    >
                        Modular
                    </Button>
                )}
                {isVisibleS && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('SummaryContent')}
                    >
                        Summary
                    </Button>
                )}
                {isVisibleTH && (
                    <Button
                        variant="contained"
                        onClick={() => setActiveTab('TransactionHistoryContent')}
                    >
                        Transaction History
                    </Button>
                )}
            </Box>

            <Box sx={{ mt: '64px', p: 2 }}>
                {renderContent()}
            </Box>
        </Box>
    );
};
