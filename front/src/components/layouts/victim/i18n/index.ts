import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import en from "./en.json";
import pl from "./pl.json";

i18n
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        resources: {
            en: {translation: en},
            pl: {translation: pl},
        },
        fallbackLng: "pl",
        supportedLngs: ["en", "pl"],
        detection: {
            order: ["navigator", "localStorage", "cookie"],
            caches: ["localStorage", "cookie"],
        },
        interpolation: {
            escapeValue: false,
        },
    }).then(r =>
    console.log(r)
);

export default i18n;