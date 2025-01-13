import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'

interface LayoutProps {
    children: ReactNode
}

export const AidOrganizationLayout = ({ children }: LayoutProps) => {
    // Udostępnia funkcję pozwalającą na zmianę widoku na inny, zgodnie z określoną ścieżką (pathname)
    const navigate = useNavigate()


    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.aid_organization.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                </Toolbar>
            </AppBar>
            <Container sx={{ p: 2 }}>
                {children}
            </Container>
        </div>
    )
}
