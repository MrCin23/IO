import { Button, Box, Typography, TextField } from '@mui/material';
import {useState} from 'react';
import { jwtDecode } from "jwt-decode";
import React from 'react';
import Cookies from "js-cookie";
import {useTranslation} from "react-i18next";

export const TransactionHistory = () => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const { t } = useTranslation();

    // Funkcja do obsługi zmiany daty
    const handleDateChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, setDate: React.Dispatch<React.SetStateAction<string>>) => {
        setDate(event.target.value);
    };

    // Funkcja do wysłania formularza
    const handleSubmit = async () => {
        const params = new URLSearchParams();
        if (startDate) params.append('startTime', new Date(startDate).toISOString());
        if (endDate) params.append('endTime', new Date(endDate).toISOString());
        const token = Cookies.get('jwt');
        if (!token) {
            throw new Error(t('report.jwtMissing'));
        }

        const decoded = jwtDecode(token);

        try {
            const response = await fetch(
                (decoded.role == 'ROLE_DARCZYŃCA' ? 'http://localhost:8080/api/transaction-history-report/generate-pdf?' : 'http://localhost:8080/api/transaction-history-report/generate-pdf/all?') + params.toString(), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`, // Dodanie tokenu JWT do nagłówków
                }
            });

            if (response.ok) {
                const blob = await response.blob();
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.download = 'transaction-history-report.pdf';
                link.click();
                window.URL.revokeObjectURL(url);
            } else {
                alert(t('report.errorReport'));
            }
        } catch (error) {
            console.error('Błąd podczas generowania raportu:', error);
            alert(t('report.errorReport'));
        }
    };

    return (
        <Box sx={{ p: 2, width: '800px', height: '600px'}}>
            <Typography variant="h6" gutterBottom sx={{ color: 'white' }}>
                {t('report.transactionHistoryTitle')}
            </Typography>

            {/* Formularz dat */}
            <TextField
                label={t('transactionHistoryFormStart')}
                type="datetime-local"
                value={startDate}
                onChange={(e) => handleDateChange(e, setStartDate)}
                sx={{ width: '100%', mb: 2, '& .MuiInputBase-input': { color: 'white' }, '& .MuiInputLabel-root': { color: 'white' },
                    backgroundColor: '#555' }}
                InputLabelProps={{
                    shrink: true,
                }}
            />

            <TextField
                label={t('report.transactionHistoryFormEnd')}
                type="datetime-local"
                value={endDate}
                onChange={(e) => handleDateChange(e, setEndDate)}
                sx={{ width: '100%', mb: 2, '& .MuiInputBase-input': { color: 'white' }, '& .MuiInputLabel-root': { color: 'white' },
                    backgroundColor: '#555' }}
                InputLabelProps={{
                    shrink: true,
                }}
            />

            {/* Przycisk generowania raportu */}
            <Button variant="contained" color="primary" onClick={handleSubmit}>
                {t('report.transactionHistoryButton')}
            </Button>
        </Box>
    );
};
