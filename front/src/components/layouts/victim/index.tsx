
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, AppBar, Toolbar, Container } from '@mui/material';
import i18n from './i18n/i18n';
import { Pathnames } from '../../../router/pathnames'

interface LayoutProps {
  children: React.ReactNode;
}

export const VictimLayout = ({ children }: LayoutProps) => {
  const navigate = useNavigate();

  const changeLanguage = (lng: string) => {
    i18n.changeLanguage(lng);
  };

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

      <Container sx={{ minHeight: 'calc(100vh - 70px)', paddingTop: '30px' }}>
        {children}
      </Container>
    </div>
  );
};