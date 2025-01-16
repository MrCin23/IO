import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import {useTranslation} from "react-i18next";
// import i18n from "@/components/layouts/volunteer/i18n";

interface LayoutProps {
    children: ReactNode
}
// i18n.changeLanguage("pl");
// i18n.changeLanguage("en");
const { t } = useTranslation();

export const VolunteerLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate()

    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.volunteer.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("home")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.volunteers)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("volunteer")}
                    </Button>
                    <Button onClick={() => navigate(Pathnames.volunteer.groups)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Volunteer groups Info
                    </Button>
                </Toolbar>
            </AppBar>
            <Container sx={{ p: 2 }}>
                {children}
            </Container>
        </div>
    )
}
