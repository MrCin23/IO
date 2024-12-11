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
import MapView from "./components/MapView";
import { createRoot } from 'react-dom/client'

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <MapView />
    </StrictMode>
);
export default MapView;
