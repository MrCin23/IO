import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import en from "./en.json";
import pl from "./pl.json";

i18n
    .use(LanguageDetector) // Wykrywanie języka przeglądarki
    .use(initReactI18next) // Integracja z React
    .init({
        resources: {
            en: {translation: en},
            pl: {translation: pl},
        },
        fallbackLng: "pl", // Domyślny język
        supportedLngs: ["en", "pl"], // Obsługiwane języki
        detection: {
            order: ["navigator", "localStorage", "cookie"], // Kolejność wykrywania
            caches: ["localStorage", "cookie"], // Gdzie zapisywać wybór użytkownika
        },
        interpolation: {
            escapeValue: false, // React już zabezpiecza przed XSS
        },
    }).then(r =>
        console.log(r)
    );

 export default i18n;
