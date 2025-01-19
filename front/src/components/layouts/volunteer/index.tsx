import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '@/router/pathnames.ts'
import { useNavigate } from 'react-router-dom'
import { useAccount } from '@/contexts/uwierzytelnianie/AccountContext.tsx'
import {useTranslation} from "react-i18next";
import i18n from "@/i18n.ts";

interface LayoutProps {
    children: ReactNode
}

export const VolunteerLayout = ({ children }: LayoutProps) => {
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
                    <Button onClick={() => navigate(Pathnames.volunteer.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("volunteer.home")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.accountPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("volunteer.myAccount")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.volunteers)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("volunteer.volunteerListTitle")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.groups)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("volunteer.volunteerGroupListTitle")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.resources)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.resourcesList")}
                    </Button>
                    <Button onClick={() => navigate('/chat')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("chat.chat")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.report.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Report
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
