import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import i18n from "@/i18n";
import {useTranslation} from "react-i18next";

interface LayoutProps {
    children: ReactNode
}

export const ReportLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate()

    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng);
    };

    const { t } = useTranslation();

    return (
        <div>
            {/* AppBar ustawione jako absolutne */}
            <AppBar position="relative" sx={{ top: 0, left: 0, width: '100%' }}>
                <Toolbar sx={{ display: 'flex' }}>
                    <Button
                        onClick={() => navigate(Pathnames.default.homePage)}
                        sx={{ my: 2, mx: 2, color: 'white' }}
                    >
                        Home
                    </Button>
                    <Button onClick={() => changeLanguage('en')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t('report.english')}
                    </Button>

                    <Button onClick={() => changeLanguage('pl')} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t('report.polish')}
                    </Button>
                </Toolbar>
            </AppBar>

            {/* Główna zawartość */}
            <div style={{  }}>
                <Container sx={{ p: 2 }}>
                    {children}
                </Container>
            </div>
        </div>
    );

}
