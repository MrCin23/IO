import { Button, Box, Typography } from '@mui/material';
import Cookies from "js-cookie";
import axios from "axios";
import {useTranslation} from "react-i18next";

export const Summary = () => {
    const { t } = useTranslation();

    const handleGenerateReport = async () => {
        try {
            // Pobranie tokenu JWT z ciasteczek
            const token = Cookies.get('jwt');
            if (!token) {
                throw new Error(t('report.jwtMissing'));
            }

            // Wysłanie żądania POST z axios
            const response = await axios.post(
                `http://localhost:8080/api/summary-report/generate-pdf`,
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
            link.download = 'summary-report.pdf'; // Ustawienie nazwy pliku
            link.click(); // Symulowanie kliknięcia w link, aby pobrać plik
            window.URL.revokeObjectURL(url); // Zwolnienie zasobów URL
        } catch (error) {
            console.error('Błąd podczas generowania raportu:', error);
            alert(t('report.errorReport'));
        }
    };

    return (
        <Box sx={{ p: 2, width: '800px', height: '600px' }}>
            {/* Opis raportu */}
            <Typography variant="h6" gutterBottom>
                {t('report.summaryTitle')}
            </Typography>

            {/* Przycisk generowania raportu */}
            <Button
                variant="contained"
                color="primary"
                onClick={() => handleGenerateReport()}
                sx={{ mt: 2 }}
            >
                {t('report.summaryButton')}
            </Button>
        </Box>
    );
};
