import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAccount } from '../contexts/uwierzytelnianie/AccountContext';
import { Role } from '../models/uwierzytelnianie/Role';

interface PrivateRouteProps {
    children: React.ReactNode;
    allowedRoles: Role['roleName'][];
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, allowedRoles }) => {
    const { account } = useAccount();

    if (!account) {
        return <Navigate to="/login" replace />;
    }

    if (!allowedRoles.includes(account.role.roleName)) {
        return <Navigate to="/" replace />;
    }

    return <>{children}</>;
};

export default PrivateRoute;