import { Button, Box, Typography, TextField } from '@mui/material';
import {useEffect, useState} from 'react';
import React from 'react';
import {Account} from "../../models/uwierzytelnianie/Account.tsx";
import Cookies from "js-cookie";
import axios from "axios";

export const TransactionHistory = () => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [user, setUser] = useState<Account | null>(null);
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
            setUser(response.data);
        };
        fetchUser();
    }, []);
    let userId: number;

    if (user) {
        userId = +user.id;
    } else {
        userId = 0;
    }


    // Funkcja do obsługi zmiany daty
    const handleDateChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, setDate: React.Dispatch<React.SetStateAction<string>>) => {
        setDate(event.target.value);
    };

    // Funkcja do wysłania formularza
    const handleSubmit = async () => {
        const params = new URLSearchParams();
        params.append('userId', userId.toString());
        if (startDate) params.append('startTime', new Date(startDate).toISOString());
        if (endDate) params.append('endTime', new Date(endDate).toISOString());

        try {
            const response = await fetch('http://localhost:8080/api/transaction-history-report/generate-pdf?' + params.toString(), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
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
                alert('Wystąpił błąd podczas generowania raportu.');
            }
        } catch (error) {
            console.error('Błąd podczas generowania raportu:', error);
            alert('Wystąpił błąd podczas generowania raportu.');
        }
    };

    return (
        <Box sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom sx={{ color: 'white' }}>
                Generowanie raportu podsumowującego
            </Typography>

            {/* Formularz dat */}
            <TextField
                label="Data początkowa"
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
                label="Data końcowa"
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
                Generuj raport podsumowujący
            </Button>
        </Box>
    );
};
