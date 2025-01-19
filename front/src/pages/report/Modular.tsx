import { Button, Box, Typography, TextField, MenuItem, Select, InputLabel, FormControl, Checkbox, ListItemText, SelectChangeEvent } from '@mui/material';
import { useState, useEffect } from 'react';
import React from 'react';
// import {Account} from "../../models/uwierzytelnianie/Account.tsx";
import Cookies from "js-cookie";
// import axios from "axios";
import {useTranslation} from "react-i18next";

export const Modular = () => {
    const [moduleId, setModuleId] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [fields, setFields] = useState<string[]>([]); // Tablica wybranych pól
    const [availableFields, setAvailableFields] = useState<string[]>([]); // Lista dostępnych pól
    const [modules, setModules] = useState<{ id: string, name: string }[]>([]); // Lista modułów
    const { t } = useTranslation();

    // const [user, setUser] = useState<Account | null>(null);
    // useEffect(() => {
    //     const fetchUser = async () => {
    //         const token = Cookies.get('jwt');
    //         if (!token) {
    //             throw new Error('Brak tokenu JWT');
    //         }
    //
    //         const response = await axios.get(`http://localhost:8080/api/auth/me`, {
    //             headers: {
    //                 Authorization: `Bearer ${token}`
    //             }
    //         });
    //         setUser(response.data);
    //     };
    //     fetchUser();
    // }, []);
    // let userId: number;
    //
    // if (user) {
    //     userId = +user.id;
    // } else {
    //     userId = 0;
    // }
    // Załaduj dostępne moduły oraz pola, gdy komponent się załaduje
    useEffect(() => {
        setModules([
            { id: '1', name: 'Moduł 1' },
            { id: '2', name: 'Moduł 2' },
            { id: '3', name: 'Moduł 3' },
        ]);
    }, []);

    // Funkcja do obsługi zmiany modułu
    const handleModuleChange = (event: SelectChangeEvent) => {
        const selectedModuleId = event.target.value;
        setModuleId(selectedModuleId);

        // Dynamiczne ładowanie pól w zależności od wybranego modułu
        if (selectedModuleId === '1') {
            setAvailableFields(['1A', '1B', '1C']);
        } else if (selectedModuleId === '2') {
            setAvailableFields(['2A', '2B', '2C']);
        } else if (selectedModuleId === '3') {
            setAvailableFields(['3A', '3B', '3C']);
        }
    };

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
        //params.append('userId', userId.toString());  // Zastąp odpowiednią wartością userId
        params.append('moduleId', moduleId);
        if (startTime) params.append('startTime', new Date(startTime).toISOString());
        if (endTime) params.append('endTime', new Date(endTime).toISOString());
        fields.forEach(field => params.append('fields[]', field));  // Użyj 'fields[]' zamiast 'fields'
        const token = Cookies.get('jwt');
        if (!token) {
            throw new Error('Brak tokenu JWT');
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
        <Box sx={{ p: 2, color: 'white' }}>
            <Typography variant="h6" gutterBottom sx={{ color: 'white' }}>
                {t('modularTitle')}
            </Typography>

            {/* Formularz */}
            <FormControl fullWidth sx={{ mb: 2, borderColor: 'white', borderWidth: 1, color: 'white' }}>
                <InputLabel sx={{ color: 'white' }}>{t('modularFormModule')}</InputLabel>
                <Select
                    value={moduleId}
                    onChange={handleModuleChange}
                    label={t('modularFormModule')}
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

            <TextField
                label={t('modularFormStart')}
                type="datetime-local"
                value={startTime}
                onChange={(e) => handleDateChange(e, setStartTime)}
                sx={{
                    width: '100%',
                    mb: 2,
                    color: 'white',
                    '& .MuiInputBase-input': {
                        color: 'white'
                    },
                    '& .MuiInputLabel-root': {
                        color: 'white',
                    },
                    '& .MuiOutlinedInput-root': {
                        borderColor: 'gray', // Szary border
                    },
                    backgroundColor: '#555', // Jasnoszare tło
                }}
                InputLabelProps={{
                    shrink: true,
                }}
            />

            <TextField
                label={t('modularFormEnd')}
                type="datetime-local"
                value={endTime}
                onChange={(e) => handleDateChange(e, setEndTime)}
                sx={{
                    width: '100%',
                    mb: 2,
                    color: 'white',
                    '& .MuiInputBase-input': {
                        color: 'white'
                    },
                    '& .MuiInputLabel-root': {
                        color: 'white',
                    },
                    '& .MuiOutlinedInput-root': {
                        borderColor: 'gray', // Szary border
                    },
                    backgroundColor: '#555', // Jasnoszare tło
                }}
                InputLabelProps={{
                    shrink: true,
                }}
            />

            <FormControl fullWidth sx={{ mb: 2, borderColor: 'white', color: 'white' }}>
                <InputLabel sx={{ color: 'white' }}>{t('modularFormFields')}</InputLabel>
                <Select
                    multiple
                    value={fields}
                    onChange={handleFieldChange}
                    label={t('modularFormFields')}
                    renderValue={(selected) => selected.join(', ')}
                    sx={{
                        '& .MuiSelect-icon': {
                            color: 'white',
                        },
                        '& .MuiInputLabel-root': {
                            color: 'white',
                        },
                        color: 'white',
                        borderColor: 'gray', // Szary border
                        backgroundColor: '#555', // Jasnoszare tło
                        '& .MuiOutlinedInput-root': {
                            borderColor: 'gray', // Szary border
                        },
                    }}
                >
                    {availableFields.map((field) => (
                        <MenuItem key={field} value={field}>
                            <Checkbox checked={fields.indexOf(field) > -1} />
                            <ListItemText primary={field} />
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>

            <Button variant="contained" color="primary" onClick={handleSubmit}>
                {t('modularButton')}
            </Button>
        </Box>
    );
};
