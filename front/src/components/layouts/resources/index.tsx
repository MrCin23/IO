import { AppBar, Button, Container, Toolbar } from '@mui/material';
import { ReactNode } from 'react';
import { Pathnames } from '../../../router/pathnames';
import { useNavigate, useLocation } from 'react-router-dom';

interface LayoutProps {
    children: ReactNode;
}

export const ResourceLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate();
    const location = useLocation();

    const getRoleFromPath = () => {
        if (location.pathname.startsWith('/organization')) return 'aid_organization';
        if (location.pathname.startsWith('/donor')) return 'donor';
        if (location.pathname.startsWith('/authority')) return 'authority_representative';
        if (location.pathname.startsWith('/victim')) return 'victim';
        if (location.pathname.startsWith('/volunteer')) return 'volunteer';
        return 'default';
    };

    const role = getRoleFromPath();

    const getButtonsForRole = () => {
        switch (role) {
            case 'aid_organization':
                return [
                    { label: 'Home', path: Pathnames.default.homePage },
                    { label: 'Warehouses list', path: Pathnames.aid_organization.warehouses },
                    { label: 'Resources list', path: Pathnames.aid_organization.resources },
                    { label: 'Create warehouse', path: Pathnames.aid_organization.createWarehouse },
                    { label: 'Create resource', path: Pathnames.aid_organization.createResource },
                ];
            case 'donor':
                return [
                    { label: 'Home', path: Pathnames.default.homePage },
                    { label: 'Account', path: Pathnames.donor.accountPage },
                    { label: 'Accounts List', path: Pathnames.donor.accountsListPage },
                    { label: 'Create resource', path: Pathnames.donor.createResource },
                ];
            case 'authority_representative':
                return [
                    { label: 'Home', path: Pathnames.default.homePage },
                    { label: 'Warehouses list', path: Pathnames.authority_representative.warehouses },
                    { label: 'Resources list', path: Pathnames.authority_representative.resources },
                    { label: 'Create warehouse', path: Pathnames.authority_representative.createWarehouse },
                    { label: 'Create resource', path: Pathnames.authority_representative.createResource },
                ];
            case 'victim':
                return [
                    { label: 'Home', path: Pathnames.default.homePage },
                    { label: 'Resources list', path: Pathnames.victim.resources },
                ];
            case 'volunteer':
                return [
                    { label: 'Home', path: Pathnames.default.homePage },
                    { label: 'Resources list', path: Pathnames.volunteer.resources },
                ];
            default:
                return [{ label: 'Home', path: Pathnames.default.homePage }];
        }
    };

    const buttons = getButtonsForRole();

    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex' }}>
                    {buttons.map(({ label, path }) => (
                        <Button
                            key={path}
                            onClick={() => navigate(path)}
                            sx={{ my: 2, mx: 2, color: 'white' }}
                        >
                            {label}
                        </Button>
                    ))}
                </Toolbar>
            </AppBar>
            <Container sx={{ p: 2 }}>{children}</Container>
        </div>
    );
};
