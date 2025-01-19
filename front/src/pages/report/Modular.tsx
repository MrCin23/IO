import { Button, Box, Typography, MenuItem, Select, InputLabel, FormControl, SelectChangeEvent } from '@mui/material';
import { useState, useEffect } from 'react';
import Cookies from "js-cookie";
import {useTranslation} from "react-i18next";

export const Modular = () => {
    const [moduleId, setModuleId] = useState('');
    const [modules, setModules] = useState<{ id: string, name: string }[]>([]); // Lista modułów
    const { t } = useTranslation();

    // Załaduj dostępne moduły, gdy komponent się załaduje
    useEffect(() => {
        setModules([
            { id: '1', name: t('report.Resources') },
            { id: '2', name: t('report.Warehouses') },
            { id: '3', name: t('report.Products') },
            { id: '4', name: t('report.Roles') },
            { id: '5', name: t('report.VolunteerGroups') },
        ]);
    }, []);

    // Funkcja do obsługi zmiany modułu
    const handleModuleChange = (event: SelectChangeEvent) => {
        const selectedModuleId = event.target.value;
        setModuleId(selectedModuleId);
    };


    // Funkcja do wysłania formularza
    const handleSubmit = async () => {
        const params = new URLSearchParams();
        params.append('moduleId', moduleId);
        const token = Cookies.get('jwt');
        if (!token) {
            throw new Error(t('report.jwtMissing'));
        }

        try {
            const response = await fetch('http://localhost:8080/api/modular-report/generate-pdf?' + params.toString(), {
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
                link.download = 'modular-report.pdf';
                link.click();
                window.URL.revokeObjectURL(url);
            } else {
                alert(t('errorReport'));
            }
        } catch (error) {
            console.error('Błąd podczas generowania raportu:', error);
            alert(t('errorReport'));
        }
    };


    return (
        <Box sx={{ p: 2, color: 'white', width: '800px', height: '600px'}}>
            <Typography variant="h6" gutterBottom sx={{ color: 'white' }}>
                {t('report.modularTitle')}
            </Typography>

            {/* Formularz */}
            <FormControl fullWidth sx={{ mb: 2, borderColor: 'white', borderWidth: 1, color: 'white' }}>
                <InputLabel sx={{ color: 'white' }}>{t('report.modularFormModule')}</InputLabel>
                <Select
                    value={moduleId}
                    onChange={handleModuleChange}
                    label={t('report.modularFormModule')}
                    sx={{
                        '& .MuiSelect-icon': {
                            color: 'white',
                        },
                        '& .MuiInputLabel-root': {
                            color: 'white',
                        },
                        color: 'white',
                        borderColor: 'white',
                        backgroundColor: '#555', // Jasnoszare tło
                        '& .MuiOutlinedInput-root': {
                            borderColor: 'gray', // Szary border
                        },
                    }}
                >
                    {modules.map((module) => (
                        <MenuItem key={module.id} value={module.id}>
                            {module.name}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>

            <Button variant="contained" color="primary" onClick={handleSubmit}>
                {t('report.modularButton')}
            </Button>
        </Box>
    );
};
