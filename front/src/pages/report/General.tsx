import { Button, Box, Typography } from '@mui/material';
import {useEffect, useState} from 'react';
import Cookies from "js-cookie";
import axios from "axios";
import {Account} from "../../models/uwierzytelnianie/Account.tsx";
import {useTranslation} from "react-i18next";

export const General = () => {
    const { t } = useTranslation();
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


    const handleGenerateReport = async (userId: number) => {
        try {
            // Pobranie tokenu JWT z ciasteczek
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error('Brak tokenu JWT');
            }

            // Wysłanie żądania POST z axios
            const response = await axios.post(
                `http://localhost:8080/api/general-report/generate-pdf?userId=${userId}`,
                null, // Brak ciała żądania w tym przypadku
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`, // Dodanie tokenu JWT do nagłówków
                    },
                    responseType: 'blob', // Oczekiwanie odpowiedzi jako plik binarny
                }
            );

            // Przetwarzanie odpowiedzi i pobieranie pliku PDF
            const blob = new Blob([response.data], { type: 'application/pdf' }); // Tworzenie obiektu Blob
            const url = window.URL.createObjectURL(blob); // Tworzenie URL do danych binarnych
            const link = document.createElement('a'); // Tworzenie elementu linku
            link.href = url;
            link.download = 'general-report.pdf'; // Ustawienie nazwy pliku
            link.click(); // Symulowanie kliknięcia w link, aby pobrać plik
            window.URL.revokeObjectURL(url); // Zwolnienie zasobów URL
        } catch (error) {
            console.error('Błąd podczas generowania raportu:', error);
            alert(t('errorReport'));
        }
    };

    return (
        <Box sx={{ p: 2 }}>
            {/* Opis raportu */}
            <Typography variant="h6" gutterBottom>
                {t('generalTitle')}
            </Typography>
            <Typography variant="body1" gutterBottom>
                {t('generalDescription')}
            </Typography>

            {/* Przycisk generowania raportu */}
            <Button
                variant="contained"
                color="primary"
                onClick={() => handleGenerateReport(userId)} // Przekazanie dynamicznego userId
                sx={{ mt: 2 }}
            >
                {t('generalButton')}
            </Button>
        </Box>
    );
};
