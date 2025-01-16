
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, AppBar, Toolbar, Container } from '@mui/material';
import i18n from './i18n/i18n';
import { Pathnames } from '../../../router/pathnames'
import { useNavigate } from 'react-router-dom'
import { useAccount } from '../../../contexts/uwierzytelnianie/AccountContext'
import {useTranslation} from "react-i18next";

interface LayoutProps {
  children: React.ReactNode;
}

export const VictimLayout = ({ children }: LayoutProps) => {
  const navigate = useNavigate();

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
                    <Button onClick={() => navigate(Pathnames.victim.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        Home
                    </Button>
                    <Button onClick={() => navigate(Pathnames.victim.accountPage)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        My Account
                    </Button>
                    <Button onClick={() => navigate(Pathnames.victim.resources)} sx={{ my: 2, mx: 2, color: 'white' }}>
                        {t("resources.resourcesList")}
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

  return (
    <div>
      <AppBar position="fixed">
        <Toolbar>
          <Button onClick={() => navigate(Pathnames.default.homePage)} sx={{ my: 2, mx: 2, color: 'white' }}>
            Home
          </Button>

          <Button onClick={() => changeLanguage('en')} sx={{ my: 2, mx: 2, color: 'white' }}>
            {i18n.t('english')}
          </Button>

          <Button onClick={() => changeLanguage('pl')} sx={{ my: 2, mx: 2, color: 'white' }}>
            {i18n.t('polish')}
          </Button>

        </Toolbar>
      </AppBar>

      <Container sx={{ minHeight: 'calc(100vh - 70px)', paddingTop: '42px' }}>
        {children}
      </Container>
    </div>
  );
};