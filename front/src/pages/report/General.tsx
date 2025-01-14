import { Button, Box, Typography } from '@mui/material';
import { useState } from 'react';

export const General = () => {
    const [userId] = useState(1); // Można to zmienić w zależności od potrzeb

    const handleGenerateReport = async (userId: number) => {
        try {
            const response = await fetch(
                `http://localhost:8080/api/general-report/generate-pdf?userId=${userId}`,
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
                link.download = 'general-report.pdf'; // Ustawiamy nazwę pliku do pobrania
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
            {/* Opis raportu */}
            <Typography variant="h6" gutterBottom>
                Generowanie raportu ogólnego
            </Typography>
            <Typography variant="body1" gutterBottom>
                Kliknięcie przycisku poniżej wywoła proces generowania raportu ogólnego w formacie PDF.
            </Typography>

            {/* Przycisk generowania raportu */}
            <Button
                variant="contained"
                color="primary"
                onClick={() => handleGenerateReport(userId)} // Przekazanie dynamicznego userId
                sx={{ mt: 2 }}
            >
                Generuj raport
            </Button>
        </Box>
    );
};
