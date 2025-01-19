import React, { useState, useEffect } from 'react';
import axios from '../../api/Axios';
import { Account } from '../../models/uwierzytelnianie/Account';
import {useTranslation} from "react-i18next";

const MyAccountPage = () => {
    const [user, setUser] = useState<Account | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [password, setPassword] = useState<string>('');
    const [message, setMessage] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);
    const { t } = useTranslation();

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const response = await axios.get(`/auth/me`);
                setUser(response.data);
            } catch (err) {
                console.error(err);
                setError(t("auth.fetchUserError"));
            }
        };

        fetchUser();
    }, []);

    const handleChangePassword = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axios.post('/auth/reset', { password });
            setSuccessMessage(t("auth.passwordChangeSuccess"));
            setPassword('');
        } catch (err) {
            console.error(err);
            setMessage(t("auth.passwordChangeError"));
        }
    };

    if (error) {
        return <div className="alert alert-danger">{error}</div>;
    }

    if (!user) {
        return <div className="alert alert-info"></div>;
    }

    return (
        <div className="container mt-5">
            <h3 className="text-center mb-4">{t("auth.userInfo")}</h3>

            {message && (
                <div className="alert alert-danger" role="alert">
                    {message}
                </div>
            )}

            <div className="row">
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">ID</h5>
                            <p className="card-text">{user.id}</p>
                        </div>
                    </div>
                </div>
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">{t("auth.username")}</h5>
                            <p className="card-text">{user.username}</p>
                        </div>
                    </div>
                </div>
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">{t("auth.status")}</h5>
                            <p className="card-text">{user.active ? 'Aktywny' : 'Nieaktywny'}</p>
                        </div>
                    </div>
                </div>
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">{t("auth.firstName")}</h5>
                            <p className="card-text">{user.firstName}</p>
                        </div>
                    </div>
                </div>
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">{t("auth.lastName")}</h5>
                            <p className="card-text">{user.lastName}</p>
                        </div>
                    </div>
                </div>
                <div className="col-md-4 mb-4">
                    <div className="card">
                        <div className="card-body text-center">
                            <h5 className="card-title">{t("auth.lastLogon")}</h5>
                            <p className="card-text">{user.lastLogin ? new Date(user.lastLogin).toLocaleString() : t("auth.noLastLogon")}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div className='row justify-content-center'>
                <div className='col-md-6'>
                    <form onSubmit={handleChangePassword} className="p-4 border rounded shadow-sm">
                        <h4 className="text-center mb-3">{t("auth.changePassword")}</h4>
                        <div className="mb-4 input-group">
                            <label htmlFor="password" className="form-label w-100">{t("auth.newPassword")}</label>
                            <input
                                type={passwordVisible ? 'text' : 'password'}
                                className="form-control"
                                id="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder={t("auth.newPasswordHint")}
                                minLength={8}
                                maxLength={50}
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
                        <button type="submit" className="btn btn-primary w-100 mb-3">
                            {t("auth.changePassword")}
                        </button>
                        {successMessage && (
                            <div className="alert alert-success" role="alert">
                                {successMessage}
                            </div>
                        )}
                    </form>
                </div>
            </div>
        </div>
    );
};

export default MyAccountPage;