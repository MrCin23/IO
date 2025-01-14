import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/TopMenu.css";

type TopMenuProps = object

const TopMenu: React.FC<TopMenuProps> = () => {
    const navigate = useNavigate();

    return (
        <nav className="top-menu">
            <button className="menu-button" onClick={() => navigate("/")}>Strona główna</button>
            <button className="menu-button" onClick={() => navigate("/financial-donations")}>Wyświetl Darowizny Finansowe</button>
            <button className="menu-button" onClick={() => navigate("/item-donations")}>Wyświetl Darowizny Rzeczowe</button>
        </nav>
    );
};

export default TopMenu;
