import { Button, Box, Typography } from '@mui/material';
import {useEffect, useState} from 'react';
import {Account} from "../../models/uwierzytelnianie/Account.tsx";
import Cookies from "js-cookie";
import axios from "axios";

export const Action = () => {
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

    // Funkcja obsługująca generowanie raportu
    const handleGenerateReport = async (userId: number) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/action-report/generate-pdf?userId=${userId}`,
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            // Jeśli odpowiedź jest poprawna (status 200), przetwarzamy plik PDF
            if (response.ok) {
                const blob = await response.blob(); // Otrzymujemy dane binarne (PDF)
                const url = window.URL.createObjectURL(blob); // Tworzymy URL do danych binarnych
                const link = document.createElement('a'); // Tworzymy element linku
                link.href = url;
                link.download = 'actions-report.pdf'; // Ustawiamy nazwę pliku do pobrania
                link.click(); // Symulujemy kliknięcie, aby pobrać plik
                window.URL.revokeObjectURL(url); // Zwolnienie zasobów URL
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
            {/* Opis sekcji Actions */}
            <Typography variant="h6" gutterBottom>
                Generowanie raportu akcji
            </Typography>
            <Typography variant="body1" gutterBottom>
                Kliknięcie przycisku poniżej wywoła proces generowania raportu akcji w formacie PDF.
            </Typography>

            {/* Przycisk generowania raportu */}
            <Button
                variant="contained"
                color="primary"
                onClick={() => handleGenerateReport(userId)} // Przekazanie dynamicznego userId
                sx={{ mt: 2 }}
            >
                Generuj raport akcji
            </Button>
        </Box>
    );
};
