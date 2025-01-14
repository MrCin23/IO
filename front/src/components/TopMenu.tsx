
import { useNavigate } from "react-router-dom";
import "../styles/TopMenu.css";
import {Pathnames} from "@/router/pathnames.ts";

function TopMenu(){
    const navigate = useNavigate();

    return (
        <nav className="top-menu">
            <button className="menu-button" onClick={() => navigate(Pathnames.donor.homePage)}>Strona główna</button>
            <button className="menu-button" onClick={() => navigate(Pathnames.donor.financialDonations)}>Wyświetl Darowizny Finansowe</button>
            <button className="menu-button" onClick={() => navigate(Pathnames.donor.itemDonations)}>Wyświetl Darowizny Rzeczowe</button>
        </nav>
    );
}

export default TopMenu;
