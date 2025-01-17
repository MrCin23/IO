import React, {useState} from "react";
import {useTranslation} from "react-i18next";
import "../styles/DonationPanel.css";
import api from "@/api/Axios.tsx";

interface FinancialDonationPanelProps {
    selectedAmount: number | "other";
    currency: "PLN" | "EUR";
    onAmountChange: (amount: number | "other") => void;
    onCurrencyChange: (currency: "PLN" | "EUR") => void;
}

const FinancialDonationPanel: React.FC<FinancialDonationPanelProps> = ({
    selectedAmount,
    currency,
    onAmountChange,
    onCurrencyChange,

}) => {
    const { t } = useTranslation();
    const [amountText, setAmountText] = useState<number | string>(t("donor.otherAmount"));
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [customAmount, setCustomAmount] = useState<string>("");
    const [needs, setNeeds] = useState<Array<[number, string]>>([]);
    const [selectedNeed, setSelectedNeed] = useState<number | "">("");

    const amounts = currency === "PLN" ? [60, 110, 220] : [20, 30, 60];

    const fetchNeeds = async () => {
        try {
            const response = await api.get(`/financial-needs`);
            const data = response.data;
            const formattedNeeds = data.map((need: any) => [need.id, need.description]);
            setNeeds(formattedNeeds);
        } catch (error) {
            console.error(error);
            alert(t("errorFetchingGoals"));
        }
    };

    const handleNeedChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        const needId = Number(e.target.value);
        setSelectedNeed(needId);
    };


    const handleAmountClick = (amount: number | "other") => {
        onAmountChange(amount);
        if (amount === "other") {
            setIsEditing(true);
            setCustomAmount(""); // Resetujemy pole
        } else {
            setIsEditing(false); // Wyłączamy tryb edycji
        }
    };

    const handleCustomAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setCustomAmount(e.target.value);
    };

    const handleCurrencyChange = (newCurrency: "PLN" | "EUR") => {
        onCurrencyChange(newCurrency);
        // Resetujemy kwoty przy zmianie waluty
        if (newCurrency === "PLN") {
            onAmountChange(60); // Domyślna kwota w PLN
        } else {
            onAmountChange(20); // Domyślna kwota w EUR
        }
    };

    const handleCustomAmountBlur = () => {
        // Walidacja
        if(customAmount !== "") {
            const parsedAmount = parseFloat(customAmount);
            if (!isNaN(parsedAmount) && parsedAmount > 0) {
                setAmountText(parsedAmount);
                onAmountChange(parsedAmount);
                setIsEditing(false);
            } else {
                alert(t("donor.invalidAmount"));
                setAmountText(t("donor.otherAmount"));
            }
        }

    };

    const handleSubmit = async () => {
        try{
            const amount = selectedAmount === "other" ? Number(prompt(t("otherAmount"), "50")) : selectedAmount;
            const payload = {
                amount,
                currency,
                needId: selectedNeed,
            };
            await api.post(`/donations/financial`, JSON.stringify(payload), {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        } catch (error) {
            console.error(error);
            alert(t("donor.donationError"));
        }
    };

    return (
        <>
            <div className="donation-currency">
                <h3>{t("donor.selectCurrency")}</h3>
                <button
                    className={`currency-button ${currency === "PLN" ? "selected" : ""}`}
                    onClick={() => handleCurrencyChange("PLN")}
                >
                    PLN
                </button>
                <button
                    className={`currency-button ${currency === "EUR" ? "selected" : ""}`}
                    onClick={() => handleCurrencyChange("EUR")}
                >
                    EUR
                </button>
            </div>

            <div className="donation-amounts">
                <h3>{t("donor.selectAmount")}</h3>
                <div className="amount-options">
                    {amounts.map((amount) => (
                        <button
                            key={amount}
                            className={`amount-button ${
                                selectedAmount === amount ? "selected" : ""
                            }`}
                            onClick={() => handleAmountClick(amount)}
                        >
                            {amount}
                        </button>
                    ))}
                    <button
                        className={`amount-button ${
                            selectedAmount === "other" ? "selected" : ""
                        }`}
                        onClick={() => handleAmountClick("other")}
                    >
                        {isEditing ? (
                            <input
                                type="text"
                                value={customAmount}
                                onChange={handleCustomAmountChange}
                                onBlur={handleCustomAmountBlur}
                                autoFocus
                            />
                        ) : (
                            amountText
                        )}
                    </button>
                </div>
            </div>
            <div className="donation-goal">
                <h3>{t("donor.selectGoal")}</h3>
                <div className="donation-goal-list">
                    <select
                        className="goal-select"
                        value={selectedNeed}
                        onChange={handleNeedChange}
                        onClick={fetchNeeds} // Pobierz dane przy kliknięciu
                    >
                        <option value="" disabled>
                            {t("donor.goalPlaceholder")}
                        </option>
                        {needs.map(([id, description]) => (
                            <option key={id} value={id}>
                                {description}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <button className="donation-submit" onClick={handleSubmit}>
                {t("donor.proceed")}
            </button>
        </>

    );
};
export default FinancialDonationPanel;