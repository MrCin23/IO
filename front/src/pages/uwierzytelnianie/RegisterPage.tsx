import React, { useState } from 'react';
import axios from '../../api/Axios';
import { AxiosError } from 'axios';
import {useTranslation} from "react-i18next";

const RegisterPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [roleName, setRoleName] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [passwordVisible, setPasswordVisible] = useState(false);
    const { t } = useTranslation();

    const roles = [
        {
            name: 'DARCZYŃCA',
            t: t("auth.roles.donor")
        }, {
            name: 'POSZKODOWANY',
            t: t("auth.roles.victim")
        }, {
            name: 'WOLONTARIUSZ',
            t: t("auth.roles.volunteer")
        }, {
            name: 'ORGANIZACJA_POMOCOWA',
            t: t("auth.roles.organization")
        }, {
            name: 'PRZEDSTAWICIEL_WŁADZ',
            t: t("auth.roles.authority")
        }
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
            setSuccessMessage(t("auth.registerSuccess"));
            setUsername('');
            setFirstName('');
            setLastName('');
            setRoleName('');
            setPassword('');
        } catch (err) {
            if (err instanceof AxiosError && err.response && err.response.data == "Username already exists!") {
                const errorMessage = t("auth.registerUsernameUnique");
                setError(errorMessage);
            } else {
                setError(t("auth.registerError"));
            }
        }
    };

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <form onSubmit={handleRegister} className="p-4 border rounded shadow-sm">
                        <h3 className="text-center mb-4">{t("auth.registerTitle")}</h3>
                        {error && (
                            <div className="alert alert-danger" role="alert">
                                {error}
                            </div>
                        )}
                        {successMessage && (
                            <div className="alert alert-success" role="alert">
                                {successMessage}
                            </div>
                        )}

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control`}
                                placeholder={t("auth.username")}
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                minLength={3}
                                maxLength={50}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control`}
                                placeholder={t("auth.firstName")}
                                value={firstName}
                                onChange={(e) => setFirstName(e.target.value)}
                                minLength={1}
                                maxLength={50}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <input
                                type="text"
                                className={`form-control`}
                                placeholder={t("auth.lastName")}
                                value={lastName}
                                onChange={(e) => setLastName(e.target.value)}
                                minLength={1}
                                maxLength={50}
                                required
                            />
                        </div>

                        <div className="mb-3">
                            <select
                                className={`form-select`}
                                value={roleName}
                                onChange={(e) => setRoleName(e.target.value)}
                                required
                            >
                                <option value="" disabled>{t("auth.chooseRole")}</option>
                                {roles.map((role) => (
                                    <option key={role.name} value={role.name}>{role.t}</option>
                                ))}
                            </select>
                        </div>

                        <div className="mb-3 position-relative">
                            <input
                                type={passwordVisible ? 'text' : 'password'}
                                className="form-control"
                                placeholder={t("auth.password")}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                minLength={8}
                                maxLength={50}
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

                        <button type="submit" className="btn btn-primary w-100">{t("auth.register")}</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default RegisterPage;