import './App.css'

import {BrowserRouter as Router} from 'react-router-dom'
import {RoutesComponent} from "./router";
import { AccountProvider } from './contexts/uwierzytelnianie/AccountContext';

export const App = () => (
    // TODO jeśli mamy jakiś komponent który musi być dostępny w całej aplikacji i być "niezmiennym", czyli np sesja użytkownika lub alerty, należy opakować nimi znacznik <Router>

    <AccountProvider>
        <Router>
            <RoutesComponent/>
        </Router>
    </AccountProvider>
)

export default App
