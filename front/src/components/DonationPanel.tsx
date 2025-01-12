import React, { useState } from "react";
import "../styles/DonationPanel.css";

const DonationPanel: React.FC = () => {
    const [selectedTab, setSelectedTab] = useState<"financial" | "item">(
        "financial"
    );
    const [selectedAmount, setSelectedAmount] = useState<number | "other">(60);
    const [selectedGoal, setSelectedGoal] = useState<string>("Wybierz cel ...");

    const handleAmountClick = (amount: number | "other") => {
        setSelectedAmount(amount);
    };

    const handleTabClick = (tab: "financial" | "item") => {
        setSelectedTab(tab);
    };

    const handleGoalChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setSelectedGoal(e.target.value);
    };

    const handleSubmit = () => {
        const amount =
            selectedAmount === "other"
                ? prompt("Podaj inną kwotę wpłaty:", "50") || 0
                : selectedAmount;

        alert(
            `Dziękujemy! Wybrano wpłatę: ${
                selectedTab === "financial" ? "Jednorazową" : "Miesięczną"
            }, Kwota: ${amount} zł, Cel: ${selectedGoal}`
        );
    };

    return (
        <div className="donation-panel">
            <div className="donation-header">
                <h2>Potrzebujący czekają na twoją pomoc</h2>
                <p>Wspierając SKPH pomagasz ubogim rodzinom, seniorom, chorym i osobom dotkniętym klęską żywiołową.</p>
            </div>

            <div className="donation-tabs">
                <button
                    className={`donation-tab ${
                        selectedTab === "financial" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("financial")}
                >
                    Darowizna finansowa
                </button>
                <button
                    className={`donation-tab ${
                        selectedTab === "item" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("item")}
                >
                    Darowizna rzeczowa
                </button>
            </div>

            <div className="donation-amounts">
                <h3>Wybieram kwotę wpłaty:</h3>
                <div className="amount-options">
                    {[60, 110, 220].map((amount) => (
                        <button
                            key={amount}
                            className={`amount-button ${
                                selectedAmount === amount ? "selected" : ""
                            }`}
                            onClick={() => handleAmountClick(amount)}
                        >
                            {amount} zł
                        </button>
                    ))}
                    <button
                        className={`amount-button ${
                            selectedAmount === "other" ? "selected" : ""
                        }`}
                        onClick={() => handleAmountClick("other")}
                    >
                        inna kwota
                    </button>
                </div>
            </div>

            <div className="donation-goal">
                <h3>Wybieram cel wpłaty:</h3>
                <div className="donation-goal-list">
                    <select
                        className="goal-select"
                        value={selectedGoal}
                        onChange={handleGoalChange}
                    >
                        <option value="">Wybierz cel ...</option>
                        <option value="Pomoc seniorom">Pomoc seniorom</option>
                        <option value="Pomoc dla powodzian">Pomoc dla powodzian</option>

                    </select>
                </div>
            </div>

            <button className="donation-submit" onClick={handleSubmit}>
                Przechodzę dalej
            </button>

            <p className="donation-footer">
                Wpłata darowizny jest dobrowolna. Wszystkie prawa zastrzeżone.
            </p>
        </div>
    );
};

export default DonationPanel;
