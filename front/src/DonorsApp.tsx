import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './DonorsApp.css'
import axios from "axios";
import DonationPanel from "./components/DonationPanel";
import TopMenu from "./components/TopMenu";
import ItemDonationList from "./components/ItemDonationList";
import { ItemDonation } from "./model/ItemDonation";
import FinancialDonationList from "./components/FinancialDonationList";
import { FinancialDonation } from "./model/FinancialDonation";
import {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import properties from "./properties/properties.ts";



function DonorsApp() {
    const { i18n } = useTranslation();
    const [financialDonations, setFinancialDonations] = useState<FinancialDonation[]>([]);
    const [itemDonations, setItemDonations] = useState<ItemDonation[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const handleLanguageChange = () => {
            const detectedLanguage = navigator.language.split("-")[0]; // np. "en" lub "pl"
            if (i18n.language !== detectedLanguage) {
                i18n.changeLanguage(detectedLanguage);
            }
        };

        // Wywołanie przy pierwszym renderowaniu
        handleLanguageChange();

        // Nasłuchiwanie zmian języka przeglądarki
        window.addEventListener("languagechange", handleLanguageChange);

        return () => {
            window.removeEventListener("languagechange", handleLanguageChange);
        };
    }, [i18n]);

    const fetchFinancialDonations = async () => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.get<FinancialDonation[]>(
                `${properties.serverAddress}/api/donations/financial/account/all`);
            setFinancialDonations(response.data);
        } catch (err) {
            setError("Nie udało się pobrać listy darowizn finansowych. Spróbuj ponownie później.");
            console.error(err);
        } finally {
            setIsLoading(false);
        }
    };

    const fetchItemDonations = async () => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.get<ItemDonation[]>(
                `${properties.serverAddress}/api/donations/item/account/all`);
            setItemDonations(response.data);
        } catch (err) {
            setError("Nie udało się pobrać listy darowizn rzeczowych. Spróbuj ponownie później.");
            console.error(err);
        } finally {
            setIsLoading(false);
        }
    };

  return (
          <Router>
              <div className="app">
                  <TopMenu />
                  <Routes>
                      <Route path="/" element={<DonationPanel />} />
                      <Route
                          path="/financial-donations"
                          element={
                              <FinancialDonationList
                                  donations={financialDonations}
                                  onFetchDonations={fetchFinancialDonations}
                                  isLoading={isLoading}
                                  error={error}
                              />
                          }
                      />
                      <Route
                          path="/item-donations"
                          element={
                              <ItemDonationList
                                  donations={itemDonations}
                                  onFetchDonations={fetchItemDonations}
                                  isLoading={isLoading}
                                  error={error}
                              />
                          }
                      />
                  </Routes>
              </div>
          </Router>
  );
}


export default DonorsApp
