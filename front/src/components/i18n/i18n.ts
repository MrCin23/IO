import i18n from 'i18next';
import en from './en.ts';
import pl from './pl.ts';
import { initReactI18next } from 'react-i18next';

const resources = {
    en: {
        translation: en
    },
    pl: {
        translation: pl
    }
};

i18n
    .use(initReactI18next)
    .init({
        resources,
        lng: 'en',
        fallbackLng: 'en',
        interpolation: {
            escapeValue: false
        }
    });

export default i18n;