import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { useAccount } from '../../../contexts/uwierzytelnianie/AccountContext'
import {useTranslation} from "react-i18next";
import i18n from "@/i18n.ts";

interface LayoutProps {
    children: ReactNode
}

export const AidOrganizationLayout = ({ children }: LayoutProps) => {
    // Udostępnia funkcję pozwalającą na zmianę widoku na inny, zgodnie z określoną ścieżką (pathname)
    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng);
    };
    const navigate = useNavigate()
    const { logout } = useAccount();
    const { t } = useTranslation();

    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.aid_organization.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.accountPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("general.account")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.resources)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.resourcesList")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.warehouses)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.warehousesList")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.createResource)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.addResource")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.createWarehouse)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.addWarehouse")}
                    </Button>
                    <Button onClick={() => navigate('/chat')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("chat.chat")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.report.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("report.report")}
                    </Button>
                    <Button onClick={() => { logout(); navigate('/')}} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("general.logout")}
                    </Button>
                    <Button onClick={() => changeLanguage('en')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {i18n.t('english')}
                    </Button>

                    <Button onClick={() => changeLanguage('pl')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {i18n.t('polish')}
                    </Button>
                </Toolbar>
            </AppBar>
            <Container sx={{ p: 2 }}>
                {children}
            </Container>
        </div>
    )
}
