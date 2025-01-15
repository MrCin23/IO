import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { useAccount } from '../../../contexts/uwierzytelnianie/AccountContext'

interface LayoutProps {
    children: ReactNode
}

export const AuthorityRepresentativeLayout = ({ children }: LayoutProps) => {
    // Udostępnia funkcję pozwalającą na zmianę widoku na inny, zgodnie z określoną ścieżką (pathname)
    const navigate = useNavigate()
    const { logout } = useAccount();


    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.authority_representative.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.authority_representative.accountsListPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Accounts list
                    </Button>
                    <Button onClick={() => navigate(Pathnames.authority_representative.accountPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        My Account
                    </Button>
                    <Button onClick={() => { logout(); navigate('/')}} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>
            <Container sx={{ p: 2 }}>
                {children}
            </Container>
        </div>
    )
}
