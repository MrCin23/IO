import { useEffect, useState } from 'react';
import axios from '../../api/Axios';
import { Account } from '../../models/uwierzytelnianie/Account';
import {useTranslation} from "react-i18next";

const AccountsListPage = () => {
    const [users, setUsers] = useState<Account[]>([]);
    const [filter, setFilter] = useState('');
    const [roleFilter, setRoleFilter] = useState('ALL');
    const { t } = useTranslation();

    useEffect(() => {
        axios.get('/auth').then((response) => setUsers(response.data));
    }, []);

    const handleActivate = async (userId: string) => {
        try {
            await axios.post(`/auth/${userId}/activate`);
            setUsers((prevUsers) =>
                prevUsers.map((user) =>
                    user.id === userId ? { ...user, active: true } : user
                )
            );
        } catch (error) {
            console.error(error);
        }
    };

    const handleDeactivate = async (userId: string) => {
        try {
            await axios.post(`/auth/${userId}/deactivate`);
            setUsers((prevUsers) =>
                prevUsers.map((user) =>
                    user.id === userId ? { ...user, active: false } : user
                )
            );
        } catch (error) {
            console.error(error);
        }
    };

    const handleChangeRole = async (userId: string, newRole: string) => {
        try {
            await axios.post(`/auth/change/${userId}`, { roleName: newRole });
            setUsers((prevUsers) =>
                prevUsers.map((user) =>
                    user.id === userId ? { ...user, role: { ...user.role, roleName: newRole } } : user
                )
            );
        } catch (error) {
            console.error(error);
        }
    };

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

    return (
        <div className="container my-5">
            <h2>{t("auth.accList")}</h2>

            <div className="mb-3">
                <select
                    className="form-select"
                    value={roleFilter}
                    onChange={(e) => setRoleFilter(e.target.value)}
                >
                    <option value="ALL">{t("auth.all")}</option>
                    {roles.map((role) => (
                        <option key={role.name} value={role.name}>{role.t}</option>
                    ))}
                </select>
            </div>

            <input
                type="text"
                className="form-control mb-3"
                placeholder={t("auth.filterUsername")}
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
            />

            <div className="table-responsive">
                <table className="table table-striped table-hover table-bordered">
                    <thead className='table-dark text-center'>
                        <tr>
                            <th>ID</th>
                            <th>{t("auth.username")}</th>
                            <th>{t("auth.firstName")}</th>
                            <th>{t("auth.lastName")}</th>
                            <th>{t("auth.lastLogon")}</th>
                            <th>{t("auth.role")}</th>
                            <th>{t("auth.status")}</th>
                            <th>{t("auth.changeRole")}</th>
                            <th>{t("auth.activation")}</th>
                        </tr>
                    </thead>
                    <tbody>
                    {users
                        .filter((user) => 
                            (roleFilter === 'ALL' || user.role.roleName === roleFilter) &&
                            user.username.toLowerCase().includes(filter.toLowerCase())
                        )
                        .map((user) => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.username}</td>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.lastLogin ? new Date(user.lastLogin).toLocaleString() : t("auth.noLastLogon")}</td>
                            <td>{roles.find(r => r.name === user.role.roleName)?.t}</td>
                            <td>{user.active ? t("auth.active") : t("auth.deactive")}</td>
                            <td>
                                <div className="d-flex align-items-center">
                                    <select defaultValue={user.role.roleName} id={`role-select-${user.id}`}>
                                        {roles.map((role) => (
                                            <option key={role.name} value={role.name}>{role.t}</option>
                                        ))}
                                    </select>
                                    <button
                                        className="btn btn-sm btn-primary ms-2"
                                        onClick={() => {
                                            const selectElement = document.getElementById(`role-select-${user.id}`) as HTMLSelectElement;
                                            const selectedRole = selectElement?.value;
                                            if (selectedRole) {
                                                handleChangeRole(user.id, selectedRole);
                                            }
                                        }}
                                    >
                                        {t("auth.changeRole")}
                                    </button>
                                </div>
                            </td>
                            <td>
                                <div className="d-flex align-items-center">
                                    <button
                                        className="btn btn-sm btn-success ms-2"
                                        disabled={user.active}
                                        onClick={() => handleActivate(user.id)}
                                    >
                                        {t("auth.activeAct")}
                                    </button>
                                    <button
                                        className="btn btn-sm btn-danger me-2"
                                        disabled={!user.active}
                                        onClick={() => handleDeactivate(user.id)}
                                    >
                                        {t("auth.deactiveAct")}
                                    </button>
                                </div>
                            </td>
                        </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AccountsListPage;