import { useEffect, useState } from 'react';
import axios from '../../api/Axios';
import { Account } from '../../models/uwierzytelnianie/Account';

const AccountsListPage = () => {
    const [users, setUsers] = useState<Account[]>([]);
    const [filter, setFilter] = useState('');
    const [roleFilter, setRoleFilter] = useState('ALL');

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

    return (
        <div className="container my-5">
            <h2>Lista użytkowników</h2>

            <div className="mb-3">
                <select
                    className="form-select"
                    value={roleFilter}
                    onChange={(e) => setRoleFilter(e.target.value)}
                >
                    <option value="ALL">WSZYSTKIE</option>
                    <option value="DARCZYŃCA">DARCZYŃCA</option>
                    <option value="POSZKODOWANY">POSZKODOWANY</option>
                    <option value="ORGANIZACJA_POMOCOWA">ORGANIZACJA_POMOCOWA</option>
                    <option value="WOLONTARIUSZ">WOLONTARIUSZ</option>
                    <option value="PRZEDSTAWICIEL_WŁADZ">PRZEDSTAWICIEL_WŁADZ</option>
                </select>
            </div>

            <input
                type="text"
                className="form-control mb-3"
                placeholder="Filtruj po nazwie użytkownika"
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
            />

            <div className="table-responsive">
                <table className="table table-striped table-hover table-bordered">
                    <thead className='table-dark text-center'>
                    <tr>
                        <th>ID</th>
                        <th>Nazwa użytkownika</th>
                        <th>Imię</th>
                        <th>Nazwisko</th>
                        <th>Ostatnie zalogowanie</th>
                        <th>Rola</th>
                        <th>Status</th>
                        <th>Akcje</th>
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
                            <td>{user.role.roleName}</td>
                            <td>{user.active ? 'Aktywny' : 'Nieaktywny'}</td>
                            <td>
                                <select defaultValue={user.role.roleName} id={`role-select-${user.id}`}>
                                        <option value="DARCZYŃCA">DARCZYŃCA</option>
                                        <option value="POSZKODOWANY">POSZKODOWANY</option>
                                        <option value="ORGANIZACJA_POMOCOWA">ORGANIZACJA_POMOCOWA</option>
                                        <option value="WOLONTARIUSZ">WOLONTARIUSZ</option>
                                        <option value="PRZEDSTAWICIEL_WŁADZ">PRZEDSTAWICIEL_WŁADZ</option>
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
                                        Zmień rolę
                                </button>
                                <div className="d-flex align-items-center">
                                    {user.active ? 'Aktywny' : 'Nieaktywny'}
                                    <button
                                        className="btn btn-sm btn-success ms-2"
                                        disabled={user.active}
                                        onClick={() => handleActivate(user.id)}
                                    >
                                        Aktywuj
                                    </button>
                                    <button
                                        className="btn btn-sm btn-danger me-2"
                                        disabled={!user.active}
                                        onClick={() => handleDeactivate(user.id)}
                                    >
                                        Dezaktywuj
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