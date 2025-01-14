//import './App.css'

import {BrowserRouter as Router} from 'react-router-dom'
import {RoutesComponent} from "./router";

export const App = () => (
    // TODO jeśli mamy jakiś komponent który musi być dostępny w całej aplikacji i być "niezmiennym", czyli np sesja użytkownika lub alerty, należy opakować nimi znacznik <Router>

    <Router>
        <RoutesComponent/>
    </Router>
)

export default App
