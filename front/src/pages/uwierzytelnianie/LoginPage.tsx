import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { AxiosError } from 'axios';
import axios from '../../api/Axios';
import { useAccount } from '../../contexts/uwierzytelnianie/AccountContext';
import {useTranslation} from "react-i18next";

const LoginPage = () => {
    const [credentials, setCredentials] = useState({ username: '', password: '' });
    const [error, setError] = useState<string | null>(null);
    const { account, login } = useAccount();
    const [passwordVisible, setPasswordVisible] = useState(false);
    const navigate = useNavigate();
    const { t } = useTranslation();

    useEffect(() => {
        if (account) {
            navigate('/');
        }
    }, [account, navigate]);

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };
  
    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await axios.post('/auth/login', credentials);
            const token = response.data;

            Cookies.set('jwt', token, { expires: 1 / 24, secure: true });

            const userResponse = await axios.get('/auth/me');
            const user = userResponse.data;
            login(user);

            navigate("/");
        } catch (err) {
            if (err instanceof AxiosError && err.response && err.response.data == "Wrong credentials") {
                const errorMessage = err.response.data || t("auth.loginErrorCred");
                setError(errorMessage);
            } else if (err instanceof AxiosError && err.response && err.response.data == "User is not active") {
                const errorMessage = err.response.data || t("auth.loginErrorActive");
                setError(errorMessage);
            } else {
                setError(t("auth.loginError"));
            }
        }
    };

    return (
        <div className="container mt-5">
            <div className="row justify-content-center">
                <div className="col-md-8">
                    <form onSubmit={handleLogin} className="p-4 border rounded shadow-sm">
                        <h3 className="text-center mb-4">{t("auth.loginTitle")}</h3>

                        {error && (
                            <div className="alert alert-danger" role="alert">
                                {error}
                            </div>
                        )}

                        <div className="mb-3">
                            <input
                                type="text"
                                className="form-control"
                                placeholder={t("auth.username")}
                                value={credentials.username}
                                onChange={(e) => setCredentials({ ...credentials, username: e.target.value })}
                                required
                            />
                        </div>
                        <div className="mb-3 position-relative">
                            <input
                                type={passwordVisible ? "text" : "password"}
                                className="form-control"
                                placeholder={t("auth.password")}
                                value={credentials.password}
                                onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
                                required
                            />
                            <button 
                                type="button" 
                                className="btn btn-outline-secondary position-absolute top-0 end-0" 
                                onClick={togglePasswordVisibility}
                                style={{ height: '100%' }}
                            >
                                <i className={passwordVisible ? "bi bi-eye-slash" : "bi bi-eye"}></i>
                            </button>
                        </div>
                        <button type="submit" className="btn btn-primary w-100">{t("auth.login")}</button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;