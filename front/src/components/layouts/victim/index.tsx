import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { VictimLayoutStyle } from './styles'

interface LayoutProps {
    children: ReactNode
}

export const VictimLayout = ({ children }: LayoutProps) => {
    const navigate = useNavigate()

    return (
        <VictimLayoutStyle>

            <AppBar position="fixed">
                <Toolbar>
                    <Button onClick={() => navigate(Pathnames.default.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                </Toolbar>
            </AppBar>

            <Container sx={{ minHeight: 'calc(100vh - 70px)', paddingTop: '30px' }}>
                {children}
            </Container>
        </VictimLayoutStyle>
    )
}
