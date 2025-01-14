import React, { useState } from 'react';
import axios from '../../api/Axios';
import { AxiosError } from 'axios';

const RegisterPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [roleName, setRoleName] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [passwordVisible, setPasswordVisible] = useState(false);

    const roles = [
        'DARCZYŃCA',
        'POSZKODOWANY',
        'ORGANIZACJA_POMOCOWA',
        'WOLONTARIUSZ',
        'PRZEDSTAWICIEL_WŁADZ'
    ];

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setSuccessMessage('');

        try {
            await axios.post('/auth/register', { 
                username, 
                password, 
                firstName, 
                lastName, 
                roleName 
            });
            setSuccessMessage('Twoje konto zostało założone. Teraz możesz się na nie zalogować.');
        } catch (err) {
            if (err instanceof AxiosError && err.response) {
                const errorMessage = err.response.data || 'Rejestracja nie powiodła się.';
                setError(errorMessage);
            } else {
                setError("Rejestracja nie powiodła się.");
            }
        }
    };

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-6 col-lg-4">
                    <form onSubmit={handleRegister} className="p-4 border rounded shadow-sm">
                        <h3 className="text-center mb-4">Rejestracja</h3>
                        {error && (
                            <div className="alert alert-danger" role="alert">
                                {error}
                            </div>
                        )}

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control ${error ? 'is-invalid' : ''}`}
                                placeholder="Nazwa użytkownika"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control ${error ? 'is-invalid' : ''}`}
                                placeholder="Imię"
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control ${error ? 'is-invalid' : ''}`}
                                placeholder="Nazwisko"
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <select
                                className={`form-select ${error ? 'is-invalid' : ''}`}
                                value={roleName}
                                onChange={(e) => setRoleName(e.target.value)}
                                required
                            >
                                <option value="" disabled>Wybierz rolę</option>
                                {roles.map((role) => (
                                    <option key={role} value={role}>{role}</option>
                                ))}
                            </select>
                        </div>

                        <div className="mb-3 position-relative">
                            <input
                                type={passwordVisible ? 'text' : 'password'}
                                className="form-control"
                                placeholder="Hasło"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                            <button
                                type="button"
                                className="btn btn-link position-absolute end-0 top-50 translate-middle-y"
                                onClick={togglePasswordVisibility}
                            >
                                <i className={`bi ${passwordVisible ? 'bi-eye-slash' : 'bi-eye'}`}></i>
                            </button>
                        </div>

                        <button type="submit" className="btn btn-primary w-100">Zarejestruj się</button>
                    </form>

                    {successMessage && (
                        <div className="alert alert-success" role="alert">
                            {successMessage}
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;