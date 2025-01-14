import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'

interface LayoutProps {
    children: ReactNode
}

export const ReportLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate()

    return (
        <div>
            {/* AppBar ustawione jako absolutne */}
            <AppBar position="absolute" sx={{ top: 0, left: 0, width: '100%' }}>
                <Toolbar sx={{ display: 'flex' }}>
                    <Button
                        onClick={() => navigate(Pathnames.default.homePage)}
                        sx={{ my: 2, mx: 2, color: 'white' }}
                    >
                        Home
                    </Button>
                    <Button
                        onClick={() => navigate(Pathnames.donor.homePage)}
                        sx={{ my: 2, mx: 2, color: 'white' }}
                    >
                        Donor
                    </Button>
                </Toolbar>
            </AppBar>

            {/* Główna zawartość */}
            <div style={{ paddingTop: '64px' /* Dostosowanie wysokości AppBar */ }}>
                <Container sx={{ p: 2 }}>
                    {children}
                </Container>
            </div>
        </div>
    );

}
