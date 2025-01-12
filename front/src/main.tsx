
import './index.css'
import { StrictMode } from "react";
import { createRoot } from 'react-dom/client'
import Notifications from "./powiadomienia/components/notifications";
import Announcement from './powiadomienia/types/announcement';
import Announcements from './powiadomienia/components/announcements';

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <div>
            <Notifications/>
            <Announcements/>
        </div>
    
    </StrictMode>
);

