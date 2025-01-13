import React, { useState, useEffect } from 'react';
import axios from '../../api/Axios';
import { Account } from '../../models/uwierzytelnianie/Account';

const MyAccountPage = () => {
    const [user, setUser] = useState<Account | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`/auth/me`);
                setUser(response.data);
            } catch (err) {
                console.error('Błąd podczas pobierania danych użytkownika:', err);
                setError('Nie udało się załadować danych użytkownika.');
            }
        };

        fetchUser();
    }, []);

    const handleChangePassword = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axios.post('/auth/reset', { password });
            setSuccessMessage('Hasło zostało zmienione pomyślnie.');
            setPassword(''); // Clear the password input after successful change
        } catch (err) {
            console.error('Błąd podczas zmiany hasła:', err);
            setMessage('Nie udało się zmienić hasła.');
        }
    };

    const formatDate = (dateString: string | null) => {
        if (!dateString) return 'Brak logowania';
        const date = new Date(dateString);
        return date.toLocaleString(); // Adjust formatting as needed
    };

    if (error) {
        return <div className="alert alert-danger">{error}</div>;
    }

    if (!user) {
        return <div className="alert alert-info">Ładowanie danych użytkownika...</div>;
    }

    return (
        <div className="container mt-5">
            <h3 className="text-center mb-4">Szczegóły użytkownika</h3>

            {successMessage && (
                <div className="alert alert-success" role="alert">
                    {successMessage}
                </div>
            )}

            {message && (
                <div className="alert alert-danger" role="alert">
                    {message}
                </div>
            )}

            <form>
                <div className="mb-4">
                    <label className="form-label">
                        <strong>ID:</strong> {user.id}
                    </label>
                </div>

                <div className="mb-4">
                    <label className="form-label">
                        <strong>Nazwa użytkownika:</strong> {user.username}
                    </label>
                </div>

                <div className="mb-4">
                    <label className="form-label">
                        <strong>Imię:</strong> {user.firstName}
                    </label>
                </div>

                <div className="mb-4">
                    <label className="form-label">
                        <strong>Nazwisko:</strong> {user.lastName}
                    </label>
                </div>

                <div className="mb-4">
                    <label className="form-label">
                        <strong>Status:</strong>
                        <span className={`badge ${user.active ? 'bg-success' : 'bg-danger'} ms-2`}>
                            {user.active ? 'Aktywny' : 'Nieaktywny'}
                        </span>
                    </label>
                </div>

                <div className="mb-4">
                    <label className="form-label">
                        <strong>Ostatnie logowanie:</strong> {formatDate(user.lastLogin)}
                    </label>
                </div>

                <h4 className="text-center mb-3">Zmień hasło</h4>
                <div>
                    <div className="mb-4 input-group">
                        <label htmlFor="password" className="form-label w-100">Nowe hasło</label>
                        <input
                            type={passwordVisible ? 'text' : 'password'}
                            className="form-control"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Wprowadź nowe hasło"
                            required
                        />
                        <button
                            type="button"
                            className="btn btn-link input-group-text border"
                            onClick={() => setPasswordVisible(!passwordVisible)}
                        >
                            <i className={`bi ${passwordVisible ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                        </button>
                    </div>
                    <button type="submit" className="btn btn-primary w-100">
                        Zmień hasło
                    </button>
                </div>
            </form>
        </div>

    );
};

export default MyAccountPage;
