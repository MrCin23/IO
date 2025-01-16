import {useState} from "react";
import { useTranslation } from "react-i18next";
import "../styles/DonationPanel.css";
import FinancialDonationPanel from "./FinancialDonationPanel.tsx";
import ItemDonationPanel from "./ItemDonationPanel.tsx";

function DonationPanel() {
    const { t } = useTranslation();
    const [selectedTab, setSelectedTab] = useState<"financial" | "item">("financial");

    const [selectedAmount, setSelectedAmount] = useState<number | "other">(60);
    const [currency, setCurrency] = useState<"PLN" | "EUR">("PLN");


    // States for item donation
    const [name, setname] = useState<string>("");
    const [description, setdescription] = useState<string>("");
    const [resourceQuantity, setresourceQuantity] = useState<number | "">(1);
    const [category, setcategory] = useState<string>("");

    const handleTabClick = (tab: "financial" | "item") => {
        setSelectedTab(tab);
    };

    return (
        <div className="donation-panel">
            <div className="donation-header">
                <h2>{t("donor.header")}</h2>
                <p>{t("donor.motivation_message")}</p>
            </div>

            <div className="donation-tabs">
                <button
                    className={`donation-tab ${
                        selectedTab === "financial" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("financial")}
                >
                    {t("donor.financialDonation")}
                </button>
                <button
                    className={`donation-tab ${
                        selectedTab === "item" ? "active" : ""
                    }`}
                    onClick={() => handleTabClick("item")}
                >
                    {t("donor.itemDonation")}
                </button>
            </div>
            {selectedTab === "financial" ? (
                <FinancialDonationPanel
                    selectedAmount={selectedAmount}
                    currency={currency}
                    onAmountChange={setSelectedAmount}
                    onCurrencyChange={setCurrency}
                />
            ) : (
                <ItemDonationPanel
                    name={name}
                    category={category}
                    description={description}
                    resourceQuantity={resourceQuantity}
                    onNameChange={setname}
                    onCategoryChange={setcategory}
                    onDescriptionChange={setdescription}
                    onQuantityChange={setresourceQuantity}
                />
            )}

            <p className="donation-footer">
                {t("donor.footer")}
            </p>
        </div>
    );
}

export default DonationPanel;
