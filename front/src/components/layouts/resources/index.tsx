import { AppBar, Button, Container, Toolbar } from '@mui/material';
import { ReactNode, useEffect } from 'react';
import { Pathnames } from '../../../router/pathnames';
import { useNavigate, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';

interface LayoutProps {
    children: ReactNode;
}

export const ResourceLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate();
    const location = useLocation();
    const { t, i18n } = useTranslation();

    useEffect(() => {
        const handleLanguageChange = () => {
            const detectedLanguage = navigator.language.split('-')[0];
            if (i18n.language !== detectedLanguage) {
                i18n.changeLanguage(detectedLanguage);
            }
        };

        handleLanguageChange();

        window.addEventListener('languagechange', handleLanguageChange);

        return () => {
            window.removeEventListener('languagechange', handleLanguageChange);
        };
    }, [i18n]);

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
                    { label: t('home'), path: Pathnames.aid_organization.homePage },
                    { label: t('warehousesList'), path: Pathnames.aid_organization.warehouses },
                    { label: t('resourcesList'), path: Pathnames.aid_organization.resources },
                    { label: t('createWarehouse'), path: Pathnames.aid_organization.createWarehouse },
                    { label: t('createResource'), path: Pathnames.aid_organization.createResource },
                ];
            case 'donor':
                return [
                    { label: t('home'), path: Pathnames.donor.homePage },
                    { label: t('donate'), path: Pathnames.donor.homePage },
/*                    { label: t('showFinancialDonations'), path: Pathnames.donor.financialDonations },
                    { label: t('showItemDonations'), path: Pathnames.donor.itemDonations },*/
                    { label: t('myAccount'), path: Pathnames.donor.accountPage },
                ];
            case 'authority_representative':
                return [
                    { label: t('home'), path: Pathnames.authority_representative.homePage },
                    { label: t('warehousesList'), path: Pathnames.authority_representative.warehouses },
                    { label: t('resourcesList'), path: Pathnames.authority_representative.resources },
                    { label: t('createWarehouse'), path: Pathnames.authority_representative.createWarehouse },
                    { label: t('createResource'), path: Pathnames.authority_representative.createResource },
                ];
            case 'victim':
                return [
                    { label: t('home'), path: Pathnames.victim.homePage },
                    { label: t('resourcesList'), path: Pathnames.victim.resources },
                ];
            case 'volunteer':
                return [
                    { label: t('home'), path: Pathnames.volunteer.homePage },
                    { label: t('resourcesList'), path: Pathnames.volunteer.resources },
                ];
            default:
                return [{ label: t('home'), path: Pathnames.default.homePage }];
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
