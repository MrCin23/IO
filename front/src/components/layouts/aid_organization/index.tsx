import { AppBar, Button, Container, Toolbar } from '@mui/material'
import { ReactNode } from 'react'
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { useAccount } from '../../../contexts/uwierzytelnianie/AccountContext'

interface LayoutProps {
    children: ReactNode
}

export const AidOrganizationLayout = ({ children }: LayoutProps) => {
    // Udostępnia funkcję pozwalającą na zmianę widoku na inny, zgodnie z określoną ścieżką (pathname)
    const navigate = useNavigate()
    const { logout } = useAccount();
    //const { t } = useTranslation();

    return (
        <div>
            <AppBar position="static">
                <Toolbar sx={{ display: 'flex'}}>
                    <Button onClick={() => navigate(Pathnames.aid_organization.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.accountPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        My Account
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.resources)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {/*{t("resources.resourcesList")}*/}
                        Resources list
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.warehouses)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {/*{t("resources.warehousesList")}*/}
                        Warehouses list
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.createResource)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {/*{t("resources.addResource")}*/}
                        Create Resource
                    </Button>
                    <Button onClick={() => navigate(Pathnames.aid_organization.createWarehouse)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {/*{t("resources.addWarehouse")}*/}
                        Create Warehouse
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
