// import { StrictMode } from 'react'
// import { createRoot } from 'react-dom/client'
// import './index.css'
// // import App from './App.tsx'
// import MapView from './components/MapView';
//
// createRoot(document.getElementById('root')!).render(
//   <StrictMode>
//     <MapView />
//   </StrictMode>,
// )
import { StrictMode } from "react";
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from "react-router";
import Layout from "./layout.tsx";
import './index.css';

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Layout />
        </BrowserRouter>
    </StrictMode>
);
