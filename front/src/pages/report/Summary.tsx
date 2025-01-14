import { Button, Box, Typography, TextField, FormControl, InputLabel, Select, MenuItem, Checkbox, ListItemText, SelectChangeEvent } from '@mui/material';
import { useState, useEffect } from 'react';
import React from 'react';

export const Summary = () => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [fields, setFields] = useState<string[]>([]); // Tablica wybranych pól
    const [availableFields, setAvailableFields] = useState<string[]>([]); // Lista dostępnych pól
    const [userId] = useState(1); // Załóżmy, że userId to 1

    useEffect(() => {
        // Załaduj dostępne pola
        setAvailableFields(['Field 1', 'Field 2', 'Field 3']); // Przykładowe pola, możesz je dostosować
    }, []);

    // Funkcja do obsługi zmiany daty
    const handleDateChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, setDate: React.Dispatch<React.SetStateAction<string>>) => {
        setDate(event.target.value);
    };

    // Funkcja do obsługi zmiany wybranych pól
    const handleFieldChange = (event: SelectChangeEvent<typeof fields>) => {
        const {
            target: { value },
        } = event;
        setFields(
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    // Funkcja do wysłania formularza
    const handleSubmit = async () => {
        const params = new URLSearchParams();
        params.append('userId', userId.toString());
        if (startDate) params.append('startTime', new Date(startDate).toISOString());
        if (endDate) params.append('endTime', new Date(endDate).toISOString());
        fields.forEach(field => params.append('fields[]', field));

        try {
            const response = await fetch('http://localhost:8080/api/summary-report/generate-pdf?' + params.toString(), {
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
                link.download = 'summary-report.pdf';
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

            {/* Formularz wyboru pól */}
            <FormControl fullWidth sx={{ mb: 2 }}>
                <InputLabel sx={{ color: 'white' }}>Wybierz pola</InputLabel>
                <Select
                    multiple
                    value={fields}
                    onChange={handleFieldChange}
                    label="Wybierz pola"
                    renderValue={(selected) => selected.join(', ')}
                    sx={{ '& .MuiInputBase-input': { color: 'white' }, '& .MuiInputLabel-root': { color: 'white' },
                        backgroundColor: '#555' }}
                >
                    {availableFields.map((field) => (
                        <MenuItem key={field} value={field}>
                            <Checkbox checked={fields.indexOf(field) > -1} />
                            <ListItemText primary={field} />
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>

            {/* Przycisk generowania raportu */}
            <Button variant="contained" color="primary" onClick={handleSubmit}>
                Generuj raport podsumowujący
            </Button>
        </Box>
    );
};
