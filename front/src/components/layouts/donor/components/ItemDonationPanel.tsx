import {useTranslation} from "react-i18next";
import React, {useState} from "react";
import "../../../../styles/DonationPanel.css";
import axios from "axios";
import properties from "../../../../properties/properties.ts";

interface ItemDonationPanelProps {
    itemName: string;
    itemCategory: string;
    itemDescription: string;
    itemQuantity: number | "";
    onNameChange: (name: string) => void;
    onCategoryChange: (category: string) => void;
    onDescriptionChange: (description: string) => void;
    onQuantityChange: (quantity: number | "") => void;
}

const ItemDonationPanel: React.FC<ItemDonationPanelProps> = ({
    itemName,
    itemCategory,
    itemDescription,
    itemQuantity,
    onNameChange,
    onCategoryChange,
    onDescriptionChange,
    onQuantityChange,
}) => {
    const { t } = useTranslation();

    const [needs, setNeeds] = useState<Array<[number, string]>>([]);
    const [selectedNeed, setSelectedNeed] = useState<number | "">("");

    const fetchNeeds = async () => {
        try {
            const response = await axios.get(`${properties.serverAddress}/api/material-needs`);
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

    const handleSubmit = async () => {
        try{
            const payload = {
                itemName,
                itemCategory,
                itemDescription,
                itemQuantity,
                needId: selectedNeed,
            };
            await axios.post(`${properties.serverAddress}/api/donations/item`, JSON.stringify(payload), {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            alert(
                `${t("alertDonationProceed")}: ${t("itemDonation")}, ${t(
                    "itemName"
                )}: ${itemName}, ${t("itemCategory")}: ${itemCategory}, ${t(
                    "itemDescription"
                )}: ${itemDescription}, ${t("itemQuantity")}: ${itemQuantity}`
            );
        } catch (error) {
            console.error(error);
            alert(t("donationError"));
        }
    };

    return (
        <>
            <div className="item-donation-fields">
                <div className="row">
                    <div className="column">
                        <h3>{t("itemName")}</h3>
                        <input
                            type="text"
                            value={itemName}
                            onChange={(e) => onNameChange(e.target.value)}
                        />
                    </div>
                    <div className="column">
                        <h3>{t("itemCategory")}</h3>
                        <select
                            value={itemCategory}
                            onChange={(e) => onCategoryChange(e.target.value)}
                        >
                            <option value="" disabled>
                                {t("selectCategory")}
                            </option>
                            <option value={"CLOTHING"}>
                                {t("categoryClothing")}
                            </option>
                            <option value={"HOUSEHOLD"}>
                                {t("categoryHousehold")}
                            </option>
                            <option value={"FOOD"}>
                                {t("categoryFood")}
                            </option>
                            <option value={"TOYS"}>
                                {t("categoryToys")}
                            </option>
                            <option value={"BOOKS"}>
                                {t("categoryBooks")}
                            </option>
                        </select>
                    </div>
                </div>

                <div className="row">
                    <div className="column-wide">
                        <h3>{t("itemDescription")}</h3>
                        <textarea
                            value={itemDescription}
                            onChange={(e) => onDescriptionChange(e.target.value)}
                        />
                    </div>
                    <div className="column-narrow">
                        <h3>{t("itemQuantity")}</h3>
                        <input
                            type="number"
                            min="1"
                            value={itemQuantity}
                            onChange={(e) =>
                                onQuantityChange(
                                    e.target.value === "" ? "" : parseInt(e.target.value)
                                )
                            }
                        />
                    </div>
                </div>
            </div>
            <div className="donation-goal">
                <h3>{t("selectGoal")}</h3>
                <div className="donation-goal-list">
                    <select
                        className="goal-select"
                        value={selectedNeed}
                        onChange={handleNeedChange}
                        onClick={fetchNeeds} // Pobierz dane przy kliknięciu
                    >
                        <option value="" disabled>
                            {t("goalPlaceholder")}
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
                {t("proceed")}
            </button>
        </>
    );
};

export default ItemDonationPanel;