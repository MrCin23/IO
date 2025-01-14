import React, {useState} from "react";
import { useTranslation } from "react-i18next";
import "../styles/DonationPanel.css";
import FinancialDonationPanel from "./FinancialDonationPanel";
import ItemDonationPanel from "./ItemDonationPanel";

const DonationPanel: React.FC = () => {
    const { t } = useTranslation();
    const [selectedTab, setSelectedTab] = useState<"financial" | "item">("financial");

    const [selectedAmount, setSelectedAmount] = useState<number | "other">(60);
    const [currency, setCurrency] = useState<"PLN" | "EUR">("PLN");

    const [selectedGoal, setSelectedGoal] = useState<number | "">("");

    // States for item donation
    const [itemName, setItemName] = useState<string>("");
    const [itemDescription, setItemDescription] = useState<string>("");
    const [itemQuantity, setItemQuantity] = useState<number | "">(1);
    const [itemCategory, setItemCategory] = useState<string>("");

    const handleTabClick = (tab: "financial" | "item") => {
        setSelectedTab(tab);
    };

    return (
        <div className="donation-panel">
            <div className="donation-header">
                <h2>{t("header")}</h2>
                <p>{t("motivation_message")}</p>
            </div>

            <div className="donation-tabs">
                <button
                    className={`donation-tab ${
                        selectedTab === "financial" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("financial")}
                >
                    {t("financialDonation")}
                </button>
                <button
                    className={`donation-tab ${
                        selectedTab === "item" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("item")}
                >
                    {t("itemDonation")}
                </button>
            </div>
            {selectedTab === "financial" ? (
                <FinancialDonationPanel
                    selectedAmount={selectedAmount}
                    currency={currency}
                    onAmountChange={setSelectedAmount}
                    onCurrencyChange={setCurrency}
                    onNeedChange={setSelectedGoal}
                />
            ) : (
                <ItemDonationPanel
                    itemName={itemName}
                    itemCategory={itemCategory}
                    itemDescription={itemDescription}
                    itemQuantity={itemQuantity}
                    onNameChange={setItemName}
                    onCategoryChange={setItemCategory}
                    onDescriptionChange={setItemDescription}
                    onQuantityChange={setItemQuantity}
                    onNeedChange={setSelectedGoal}
                />
            )}

            <p className="donation-footer">
                {t("footer")}
            </p>
        </div>
    );
};

export default DonationPanel;
